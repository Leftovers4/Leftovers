package bl.promotionbl.impl;

import po.promotion.PromotionPO;
import vo.order.OrderVO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;


/**
 * Created by kevin on 2016/11/18.
 */
public class Context{

    private Sale sale;

    private Level level;

    public Context(PromotionPO promotionPO) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        super();
        String promotionName = promotionPO.getPromotionType().toString();
        Class<?> promotionClass = Class.forName("bl.promotionbl.impl." + promotionName);
        Constructor<?>[] constructors = promotionClass.getConstructors();
        Promotion promotion = (Promotion) constructors[0].newInstance(promotionPO);

        if (promotion instanceof Sale){
            sale = (Sale)promotion;
        }

        if (promotion instanceof Level){
            level = (Level)promotion;
        }
    }

    public double getActualPrice(OrderVO orderVO) throws RemoteException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return sale.getActualPrice(orderVO);
    }

    public int getLevel(double credit){
        return level.getLevel(credit);
    }

}
