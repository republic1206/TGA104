package com.tibame.designer.model;
import java.util.*;
public interface DesignerOrderDAO_interface {
	public void insertinquiry(DesignerOrderVO designerOrderVO);
	public void updateQuotation(DesignerOrderVO designerOrderVO);//新增及更新報價單資訊
	public void updateContract(DesignerOrderVO designerOrderVO);//新增及更新報價單資訊
	public void updateFinished(DesignerOrderVO designerOrderVO);
	public DesignerOrderVO findDesignerOrder(Integer orderNo);
	public List<DesignerOrderVO> getAllMyOrder(Integer designerNo);//查詢該設計師的所有案件
	public List<DesignerOrderVO> getAllMyINGOrder(Integer designerNo);//查詢該設計師的進行中案件
	public List<DesignerOrderVO> getAllMyisFinishOrder(Integer designerNo); //如結案狀態為結案則取得設計師結案訂單
	public List<DesignerOrderVO> getMemberAllMyOrder(Integer memberNo);//查詢該使用者的所有裝潢訂單		
	public List<DesignerOrderVO> getAllMyQuotation(Integer designerNo);//查詢該設計師的所有報價
	public List<DesignerOrderVO> getMemberAllMyQuotation(Integer memberNo);//查詢該使用者的所有裝潢報價	
	public List<DesignerOrderVO> getAllMyContract(Integer designerNo);//查詢該設計師的所有合約
	public List<DesignerOrderVO> getMemberAllMyContract(Integer memberNo);//查詢該使用者的所有裝潢合約

}
