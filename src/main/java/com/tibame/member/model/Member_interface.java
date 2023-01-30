package com.tibame.member.model;

import java.util.List;

public interface Member_interface {
        public void insert(MemberVO memberVO);
        public void update(MemberVO memberVO);
        public void delete(Integer  memberNo);
        public MemberVO findByPrimaryKey(Integer memberNo);
        public List<MemberVO> getAll();
        //萬用複合查詢(傳入參數型態Map)(回傳 List)
//      public List<MemberVO> getAll(Map<String, String[]> map); 

}
