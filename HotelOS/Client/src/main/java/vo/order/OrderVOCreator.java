package vo.order;

import bl.orderbl.impl.Order;

/**
 * Created by lib on 2016/12/1.
 */
public class OrderVOCreator {

    /**
     * 传给界面：创建包含酒店工作人员查看酒店评价的界面信息的vo
     *
     */
    public ReviewVO create(Order order) {
        ReviewVO res = new ReviewVO();

        res.orderID = order.getHotelID();
        res.review = order.getReview();
        res.rating = order.getRating();
        res.roomType = order.getRoomType();
        res.reviewTime = order.getReviewTime();
        res.username = order.getUsername();

        return res;
    }

}