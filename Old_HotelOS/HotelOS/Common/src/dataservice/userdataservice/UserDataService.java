package dataservice.userdataservice;

import dataservice.DataServiceParent;
import po.user.UserPO;
import util.UserType;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Hiki on 2016/10/16.
 */
public interface UserDataService extends DataServiceParent {

    // 根据用户名查找用户
    public UserPO findByUsername(String username) throws RemoteException;

    // 判断用户能否登录（判断是否能同时登录）
    // public boolean canLogin(UserPO userPO) throws RemoteException;

    // 根据用户lexington显示用户列表
    public ArrayList<UserPO> findByType(UserType userType) throws RemoteException;

    // 增加一个用户
    public void insert(UserPO UserPO) throws RemoteException;

    // 删除一个用户
    public void delete(UserPO UserPO) throws RemoteException;

    // 更新一个用户
    public void update(UserPO UserPO) throws RemoteException;

    // 显示用户列表
    public ArrayList<UserPO> getList() throws RemoteException;

    // 增加用户信用值
    public void addCredit(String username, double amount) throws RemoteException;

    // 扣除用户信用值
    public void deductCredit(String username, double amount) throws RemoteException;



}
