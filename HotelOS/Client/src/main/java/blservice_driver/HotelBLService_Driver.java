package blservice_driver;

import blservice.hotelblservice.HotelBLService;
import blservice_stub.HotelBLService_Stub;
import org.junit.Before;
import org.junit.Test;
import util.Address;
import util.ResultMessage;
import util.TradingArea;
import vo.hotel.HotelVO;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by kevin on 2016/10/16.
 */
public class HotelBLService_Driver {
    HotelBLService tested;

    @Before
    public void setUp() throws Exception {
        tested = new HotelBLService_Stub();
    }

    @Test
    public void find() throws Exception {
        HotelVO res = tested.findHotelByID(123456);
        printHotelVO(res);
    }

    @Test
    public void showList() throws Exception {
        List<HotelVO> res = tested.findHotelsByConditions(new HotelVO("如家", "工作人员", 4, 4.3, "南京", "新街口地区", "好", "Wifi"));
        for (int i = 0; i < res.size(); i++) {
            printHotelVO(res.get(i));
        }
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.SUCCESS, tested.addHotel(new HotelVO("如家", "工作人员", 4, 4.3, "南京", "新街口地区", "好", "Wifi")));
    }

    @Test
    public void del() throws Exception {
        assertEquals(ResultMessage.SUCCESS, tested.deleteHotel(123456));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.SUCCESS, tested.updateHotelInfo(new HotelVO("如家", "工作人员", 4, 4.3, "南京", "新街口地区", "好", "Wifi")));
    }

    private void printHotelVO(HotelVO hotelVO){
        System.out.println(hotelVO.hotelID);
        System.out.println(hotelVO.hotelName);
        System.out.println(hotelVO.address);
        System.out.println(hotelVO.tradingArea);
        System.out.println(hotelVO.rooms.get(0).roomType);
        System.out.println(hotelVO.description);
        System.out.println(hotelVO.reviews.get(0).review);
    }
}