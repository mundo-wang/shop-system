package com.ujs.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujs.shop.common.dto.*;
import com.ujs.shop.common.enums.ResponseCodeEnum;
import com.ujs.shop.common.exception.ServiceException;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.GoodsPO;
import com.ujs.shop.common.po.PackageDetailPO;
import com.ujs.shop.common.po.PackagePO;
import com.ujs.shop.common.ro.AddOrUpdateGoodsRO;
import com.ujs.shop.common.ro.AddPackageRO;
import com.ujs.shop.common.ro.ChangePackageStatusRO;
import com.ujs.shop.common.ro.UpdatePackageRO;
import com.ujs.shop.mapper.GoodsMapper;
import com.ujs.shop.mapper.PackageDetailMapper;
import com.ujs.shop.mapper.PackageMapper;
import com.ujs.shop.service.PackageDetailService;
import com.ujs.shop.service.PackageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:32
 */


@Service
public class PackageServiceImpl extends ServiceImpl<PackageMapper, PackagePO> implements PackageService {


    @Autowired
    private PackageMapper packageMapper;


    @Autowired
    private PackageDetailMapper packageDetailMapper;


    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 拿到所有商品和套餐名
     * @return
     */
    private Set<String> goodsAndPackageNames() {
        List<GoodsPO> list1 = goodsMapper.selectList(null);
        List<PackagePO> list2 = packageMapper.selectList(null);
        Set<String> result = list1.stream().map(GoodsPO::getName).collect(Collectors.toSet());
        Set<String> packageNames = list2.stream().map(PackagePO::getName).collect(Collectors.toSet());
        result.addAll(packageNames);
        return result;
    }


    /**
     * 将套餐内的商品插入表
     */
    private void addPackageGoods(List<AddOrUpdateGoodsRO> goodsList, String packageId) {
        List<PackageDetailPO> packageDetailPOList = goodsList.stream().map(item -> {
            PackageDetailPO packageDetailPO = new PackageDetailPO();
            packageDetailPO.setId(ConstantBean.getUUID());
            packageDetailPO.setPackageId(packageId);
            BeanUtils.copyProperties(item, packageDetailPO);
            return packageDetailPO;
        }).collect(Collectors.toList());

        for (PackageDetailPO packageDetailPO : packageDetailPOList) {
            packageDetailMapper.insert(packageDetailPO);
        }
    }


    /**
     * 检验套餐内商品的数量是否超过库存上线或者已被禁售
     * @param goodsList
     * @return
     */
    private void checkGoodsAmountAndStatus(List<AddOrUpdateGoodsRO> goodsList) {
        for (AddOrUpdateGoodsRO ro : goodsList) {
            GoodsPO goodsPO = goodsMapper.selectById(ro.getGoodsId());
            if (goodsPO.getAllowance() < ro.getAmount()) {
                throw new ServiceException(ResponseCodeEnum.OUT_OF_ALLOWANCE);
            }
            if (goodsPO.getStatus()) {
                throw new ServiceException(ResponseCodeEnum.GOODS_DISABLE);
            }
        }
    }



    @Override
    @Transactional
    public void addPackage(AddPackageRO addPackageRO) {
        if (goodsAndPackageNames().contains(addPackageRO.getName())) {
            throw new ServiceException(ResponseCodeEnum.PACKAGE_NAME_UNIQUE);
        }
        PackagePO packagePO = new PackagePO();
        packagePO.setId(ConstantBean.getUUID());
        BeanUtils.copyProperties(addPackageRO, packagePO);
        packageMapper.insert(packagePO);
        List<AddOrUpdateGoodsRO> goodsList = addPackageRO.getGoodsList();
        checkGoodsAmountAndStatus(goodsList);
        addPackageGoods(goodsList, packagePO.getId());
    }

    @Override
    @Transactional
    public void updatePackage(UpdatePackageRO updatePackageRO) {
        PackagePO packagePO = packageMapper.selectById(updatePackageRO.getId());
        if (packagePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_PACHAGE);
        }
        if (! packagePO.getName().equals(updatePackageRO.getName())) {
            if (goodsAndPackageNames().contains(updatePackageRO.getName())) {
                throw new ServiceException(ResponseCodeEnum.PACKAGE_NAME_UNIQUE);
            }
        }
        BeanUtils.copyProperties(updatePackageRO, packagePO);
        packageMapper.updateById(packagePO);
        LambdaQueryWrapper<PackageDetailPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PackageDetailPO::getPackageId, packagePO.getId());
        packageDetailMapper.delete(wrapper);
        List<AddOrUpdateGoodsRO> goodsList = updatePackageRO.getGoodsList();
        checkGoodsAmountAndStatus(goodsList);
        addPackageGoods(goodsList, packagePO.getId());
    }

    @Override
    public PackageInfoDTO getPackageInfo(String id) {
        PackagePO packagePO = packageMapper.selectById(id);
        if (packagePO == null) {
            throw new ServiceException(ResponseCodeEnum.NO_SUCH_PACHAGE);
        }
        PackageInfoDTO packageInfoDTO = new PackageInfoDTO();
        BeanUtils.copyProperties(packagePO, packageInfoDTO);
        LambdaQueryWrapper<PackageDetailPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PackageDetailPO::getPackageId, packagePO.getId());
        List<PackageDetailPO> packageDetailPOS = packageDetailMapper.selectList(wrapper);
        List<PackageGoodsDTO> goodsList = packageDetailPOS.stream().map(item -> {
            GoodsPO goodsPO = goodsMapper.selectById(item.getGoodsId());
            PackageGoodsDTO packageGoodsDTO = new PackageGoodsDTO();
            BeanUtils.copyProperties(goodsPO, packageGoodsDTO);
            LambdaQueryWrapper<PackageDetailPO> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(PackageDetailPO::getPackageId, item.getPackageId());
            wrapper1.eq(PackageDetailPO::getGoodsId, item.getGoodsId());
            PackageDetailPO packageDetailPO = packageDetailMapper.selectOne(wrapper1);
            packageGoodsDTO.setAmount(packageDetailPO.getAmount());
            return packageGoodsDTO;
        }).collect(Collectors.toList());
        packageInfoDTO.setGoodsList(goodsList);
        return packageInfoDTO;
    }

    @Override
    @Transactional
    public void removePackage(List<String> packageIds) {
        for (String id : packageIds) {
            PackagePO packagePO = packageMapper.selectById(id);
            if (packagePO == null) {
                throw new ServiceException(ResponseCodeEnum.NO_SUCH_PACHAGE);
            }
            if (! packagePO.getStatus()) {
                throw new ServiceException(ResponseCodeEnum.PACKAGE_ON_SALE);
            }
        }
        LambdaQueryWrapper<PackageDetailPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PackageDetailPO::getPackageId, packageIds);
        packageDetailMapper.delete(wrapper);
        packageMapper.deleteBatchIds(packageIds);
    }

    @Override
    @Transactional
    public void changeStatus(List<String> packageIds, Boolean status) {
        for (String id : packageIds) {
            PackagePO packagePO = packageMapper.selectById(id);
            if (packagePO == null) {
                throw new ServiceException(ResponseCodeEnum.NO_SUCH_PACHAGE);
            }
            packagePO.setStatus(status);
            packageMapper.updateById(packagePO);
        }
    }

    @Override
    public PageFormBean<PackagePageDTO> packagePage(Integer page, Integer size, String name, String categoryId) {
        Page<PackagePageDTO> page1 = new Page<>(page, size);
        IPage<PackagePageDTO> page2 = packageMapper.packagePage(page1, name, categoryId);
        return new PageFormBean<>(page2);
    }

    @Override
    public List<PackByCateDTO> getPackByCate(String categoryId) {
        List<PackByCateDTO> list = new ArrayList<>();
        LambdaQueryWrapper<PackagePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PackagePO::getCategoryId, categoryId);
        wrapper.eq(PackagePO::getStatus, false);
        wrapper.orderByDesc(PackagePO::getCreateTime);
        List<PackagePO> packagePOS = packageMapper.selectList(wrapper);
        for (PackagePO packagePO : packagePOS) {
            PackByCateDTO packByCateDTO = new PackByCateDTO();
            BeanUtils.copyProperties(packagePO, packByCateDTO);
            packByCateDTO.setType(true);
            list.add(packByCateDTO);
        }
        return list;
    }

    @Override
    public List<GetGoodsDTO> getGoods(String packageId) {
        List<GetGoodsDTO> list = new ArrayList<>();
        LambdaQueryWrapper<PackageDetailPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PackageDetailPO::getPackageId, packageId);
        wrapper.orderByDesc(PackageDetailPO::getCreateTime);
        List<PackageDetailPO> packageDetailPOS = packageDetailMapper.selectList(wrapper);
        for (PackageDetailPO packageDetailPO : packageDetailPOS) {
            GetGoodsDTO getGoodsDTO = new GetGoodsDTO();
            getGoodsDTO.setNumber(packageDetailPO.getAmount());
            GoodsPO goodsPO = goodsMapper.selectById(packageDetailPO.getGoodsId());
            BeanUtils.copyProperties(goodsPO, getGoodsDTO);
            list.add(getGoodsDTO);
        }
        return list;
    }

}
