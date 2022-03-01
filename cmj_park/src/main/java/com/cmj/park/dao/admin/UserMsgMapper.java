package com.cmj.park.dao.admin;

import com.cmj.park.domain.vo.UserMsg;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 用户信息修改 Mapper
 *
 */
@Mapper
public interface UserMsgMapper {

    // 根据关键字查询
    @Select("<script>select * from t_user as u left join t_card_money as cm on u.username = cm.card_owner <where> <if test=\" username != null\"> and username like CONCAT('%', #{username}, '%')</if> </where> </script> ")
    public List<UserMsg> selectNew(UserMsg userMsg);

    // 添加用户信息
    @Insert("INSERT INTO t_user(username,realname,password,phone,email) VALUES(#{username}, #{realName}, #{password}, #{phone}, #{email}) ")
    public int insert(UserMsg userMsg);

    // 通过id删除用户
    @Delete("DELETE FROM t_user WHERE id = #{id}")
    public int delete(Integer id);

    // 更新管理员
    @Update("update t_user set username=#{username}, password=#{password}, realname=#{realName}, phone=#{phone},email=#{email} where id = #{id}")
    public int update(UserMsg userMsg);

    // 添加用户对应缴费卡
    @Insert("INSERT INTO t_card_money(card_number,card_money,card_owner) VALUES(#{cardNumber}, #{cardMoney}, #{username}) ")
    public int insertCard(UserMsg userMsg);

    // 充值
    @Update("update t_card_money set card_money=ROUND(card_money+#{cardMoney},2) where card_owner = #{username}")
    public int updateCard(UserMsg userMsg);

    //查询对应值是否存在
    @Select("<script> select count(*) from t_user where ${columnName} = #{user} <if test=\"id != null\"> and id not in (#{id})</if> </script> ")
    public int selOnly(String columnName, String user, Integer id);
}
