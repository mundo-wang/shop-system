CREATE TABLE shop_address (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    user_id VARCHAR(255) NOT NULL COMMENT '用户ID',
    receiver VARCHAR(255) NOT NULL COMMENT '收件人',
    gender BOOLEAN DEFAULT NULL COMMENT '性别，true为男，false为女',
    phone VARCHAR(255) NOT NULL COMMENT '手机号',
    address VARCHAR(1024) NOT NULL COMMENT '详细地址',
    label VARCHAR(255) DEFAULT NULL COMMENT '地址标签',
    is_default BOOLEAN DEFAULT FALSE COMMENT '是否默认地址，true为默认',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表';

CREATE TABLE shop_cart (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    user_id VARCHAR(255) NOT NULL COMMENT '用户ID',
    goods_id VARCHAR(255) NOT NULL COMMENT '商品ID',
    package_id VARCHAR(255) DEFAULT NULL COMMENT '套餐ID',
    goods_config VARCHAR(255) DEFAULT NULL COMMENT '商品配置',
    name VARCHAR(255) NOT NULL COMMENT '商品名称',
    image VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
    number INT NOT NULL COMMENT '商品数量',
    amount DECIMAL(10, 2) NOT NULL COMMENT '商品金额',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

CREATE TABLE shop_category (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    name VARCHAR(255) NOT NULL COMMENT '分类名称',
    type BOOLEAN DEFAULT FALSE COMMENT '分类类型，false为商品分类，true为套餐分类',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

CREATE TABLE shop_customer (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    user_name VARCHAR(255) NOT NULL COMMENT '用户名',
    phone VARCHAR(255) NOT NULL COMMENT '手机号',
    gender BOOLEAN DEFAULT NULL COMMENT '性别，true为男，false为女',
    picture VARCHAR(255) DEFAULT NULL COMMENT '头像',
    status BOOLEAN DEFAULT TRUE COMMENT '账户状态，true为启用，false为禁用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

CREATE TABLE shop_employee (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    user_name VARCHAR(255) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    real_name VARCHAR(255) NOT NULL COMMENT '真实姓名',
    phone VARCHAR(255) NOT NULL COMMENT '手机号',
    gender BOOLEAN DEFAULT FALSE COMMENT '性别，false为男，true为女',
    id_number VARCHAR(255) DEFAULT NULL COMMENT '身份证号',
    status BOOLEAN DEFAULT FALSE COMMENT '用户状态，false为启用，true为禁用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

CREATE TABLE shop_goods_config (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    goods_id VARCHAR(255) NOT NULL COMMENT '商品ID',
    name VARCHAR(255) NOT NULL COMMENT '配置名称',
    value VARCHAR(255) NOT NULL COMMENT '配置值',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品配置表';

CREATE TABLE shop_goods (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    name VARCHAR(255) NOT NULL COMMENT '商品名称',
    category_id VARCHAR(255) NOT NULL COMMENT '商品分类ID',
    price DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    allowance INT NOT NULL COMMENT '商品余量',
    image VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
    description TEXT DEFAULT NULL COMMENT '商品描述',
    status BOOLEAN DEFAULT TRUE COMMENT '商品状态，0为停售，1为启售',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

CREATE TABLE shop_order_detail (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    name VARCHAR(255) NOT NULL COMMENT '商品名称',
    image VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
    order_id VARCHAR(255) NOT NULL COMMENT '订单ID',
    goods_id VARCHAR(255) NOT NULL COMMENT '商品ID',
    package_id VARCHAR(255) DEFAULT NULL COMMENT '套餐ID',
    number INT NOT NULL COMMENT '商品数量',
    amount DECIMAL(10, 2) NOT NULL COMMENT '商品金额',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

CREATE TABLE shop_order (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    address_id VARCHAR(255) NOT NULL COMMENT '地址ID',
    user_id VARCHAR(255) NOT NULL COMMENT '用户ID',
    number VARCHAR(255) NOT NULL COMMENT '订单编号',
    status BOOLEAN DEFAULT FALSE COMMENT '订单状态，0为待处理，1为已处理',
    amount DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
    remark VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE shop_package_detail (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    package_id VARCHAR(255) NOT NULL COMMENT '套餐ID',
    goods_id VARCHAR(255) NOT NULL COMMENT '商品ID',
    amount INT NOT NULL COMMENT '商品数量',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐详情表';

CREATE TABLE shop_package (
    id VARCHAR(255) NOT NULL COMMENT '主键',
    category_id VARCHAR(255) NOT NULL COMMENT '分类ID',
    name VARCHAR(255) NOT NULL COMMENT '套餐名称',
    price DECIMAL(10, 2) NOT NULL COMMENT '套餐价格',
    image VARCHAR(255) DEFAULT NULL COMMENT '套餐图片',
    description TEXT DEFAULT NULL COMMENT '套餐描述',
    status BOOLEAN DEFAULT FALSE COMMENT '套餐状态，false为启售，true为停售',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user VARCHAR(255) NOT NULL COMMENT '创建用户',
    update_user VARCHAR(255) DEFAULT NULL COMMENT '更新用户',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除，0为未删除，1为已删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐表';
