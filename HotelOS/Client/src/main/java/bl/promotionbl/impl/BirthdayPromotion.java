package bl.promotionbl.impl;

import bl.userbl.impl.User;
import po.promotion.PromotionPO;
import vo.order.OrderVO;

import java.time.LocalDateTime;

/**
 * Created by kevin on 2016/11/6.
 */
public class BirthdayPromotion implements Sale{
    private double discount;

    public BirthdayPromotion(PromotionPO promotionPO) {
        this.discount = promotionPO.getDiscount();
    }

    @Override
    public double getActualPrice(OrderVO orderVO) {
        //todo get user
        User user = null;
        double price = orderVO.orderPriceVO.originPrice;
        LocalDateTime today = orderVO.orderTimeVO.generateTime;

        //todo confirm birthday
        if (true){
            return price * discount;
        }else {
            return price;
        }
    }
}