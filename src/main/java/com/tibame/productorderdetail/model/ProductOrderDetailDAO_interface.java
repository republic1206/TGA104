package com.tibame.productorderdetail.model;

import java.util.List;

public interface ProductOrderDetailDAO_interface {
	public void insert(ProductOrderDetailVO productOrderDetailVO);
    public void update(ProductOrderDetailVO productOrderDetailVO);
    public void delete(Integer orderDetailNo);
    public ProductOrderDetailVO findByPrimaryKey(Integer orderDetailNo);
    public List<ProductOrderDetailVO> getAll();
}
