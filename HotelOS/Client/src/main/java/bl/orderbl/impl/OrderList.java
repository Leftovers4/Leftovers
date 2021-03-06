package bl.orderbl.impl;

import bl.hotelbl.impl.Timeline;
import po.order.OrderPO;
import util.RoomType;
import util.TableName;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2016/11/6.
 */
public class OrderList extends ArrayList<OrderPO>{

    public OrderList(List<OrderPO> orderPOList){
        for (int i = 0; i < orderPOList.size(); i++) {
            this.add(orderPOList.get(i));
        }
        sortByTime();
    }

    /**
     * 获取酒店的平均评分
     *
     * @return 酒店的平均评分
     */
    public double getHotelRating(){
        double sum = 0;

        for (OrderPO orderPO : this) {
            sum += orderPO.getReviewPO().getRating();
        }

        sum = this.size() == 0 ? sum : sum / this.size();

        return Math.round(sum * 100) / 100;
    }

    /**
     * 去除掉酒店重复的订单，即列表中不能包含两个或以上的酒店相同的订单
     */
    public OrderList filterByDifferentHotels(){
        OrderList res = new OrderList(new ArrayList<>());

        for (int i = 0; i < this.size(); i++) {
            boolean same = false;

            for (int j = 0; j < res.size(); j++) {
                if (this.get(i).getHotelID() == res.get(j).getHotelID()){
                    same = true;
                    break;
                }
            }

            if (!same)
                res.add(this.get(i));
        }

        return res;
    }

    /**
     * 根据有评价筛选订单
     *
     * @return 有评价的订单
     */
    public OrderList filterByHasReview(){
        OrderList res = new OrderList(new ArrayList<>());

        for (int i = 0; i < this.size(); i++) {
            if (new Order(this.get(i)).hasReview()) {
                res.add(this.get(i));
            }
        }

        return res;
    }

    /**
     * 根据评分筛选订单
     *
     * @param rating 评分
     * @return 评分是rating的订单
     */
    public OrderList filterByRating(int rating){
        OrderList res = new OrderList(new ArrayList<>());

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getReviewPO().getRating() == rating){
                res.add(this.get(i));
            }
        }

        return res;
    }

    /**
     * 根据房间类型筛选订单
     *
     * @param roomType 房间类型
     * @return 房间类型符合roomTyoe的订单
     */
    public OrderList filterByRoomType(RoomType roomType){
        OrderList res = new OrderList(new ArrayList<>());

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getRoomType().equals(roomType)){
                res.add(this.get(i));
            }
        }

        return res;
    }

    /**
     * 根据未过期筛选订单
     *
     * @return 未过期的订单
     */
    public OrderList filterByIsNotOverdue(){
        OrderList res = new OrderList(new ArrayList<>());

        for (int i = 0; i < this.size(); i++) {
            if (!new Order(this.get(i)).isOverdue()){
                res.add(this.get(i));
            }
        }

        return res;
    }

    /**
     * 填充某房间类型的时间线
     *
     * @param timeline 某房间类型的时间线
     */
    public void fillTimeline(Timeline timeline){
        for (int i = 0; i < this.size(); i++) {
            timeline.addPeriod(new Order(this.get(i)).getOrderedPeriod(), this.get(i).getRoomAmount());
        }
    }

    /**
     * 根据生成时间升序对订单排序
     */
    public OrderList sortByTime(){
        int num = this.size();

        for (int i = 0; i < num - 1; i++) {
            int chosenKeyValueIndex = 0;
            LocalDateTime chosenKeyValue = this.get(0).getOrderTimePO().getGenerateTime();

            for (int j = 1; j < num - i; j++) {
                LocalDateTime keyValue = this.get(j).getOrderTimePO().getGenerateTime();

                boolean compareValue = chosenKeyValue.isAfter(keyValue);

                if (compareValue == true){
                    chosenKeyValueIndex = j;
                    chosenKeyValue = keyValue;
                }
            }

            OrderPO tempOrderPO = this.get(chosenKeyValueIndex);
            this.remove(chosenKeyValueIndex);
            this.add(num - 1 - i, tempOrderPO);
        }

        return this;
    }

}
