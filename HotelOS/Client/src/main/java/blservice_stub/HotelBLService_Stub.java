package blservice_stub;

import blservice.hotelblservice.HotelBLService;
import util.Address;
import util.ResultMessage;
import util.RoomType;
import util.TradingArea;
import vo.hotel.HotelVO;
import vo.hotel.RoomVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2016/10/16.
 */
public class HotelBLService_Stub implements HotelBLService {
    @Override
    public ResultMessage addHotel(HotelVO hotelVO) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage deleteHotel(long hotelID) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateHotelInfo(HotelVO hotelVO) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public HotelVO findHotelByID(long hotelID) {
        return new HotelVO("如家", Address.NANJING, TradingArea.XIANLIN_CENTER, "好", "Wifi", null);
    }

    @Override
    public List<HotelVO> findHotelsByConditions(HotelVO hotelVO) {
        ArrayList<HotelVO> list = new ArrayList<HotelVO>();
        list.add(new HotelVO("如家", Address.NANJING, TradingArea.XIANLIN_CENTER, "好", "Wifi", null));
        return list;
    }

    @Override
    public List<HotelVO> findHotelsByUsername(String username) {
        return null;
    }

    @Override
    public void sortHotels(List<HotelVO> hotelVOs, String key, int mode) {

    }

    @Override
    public ResultMessage addRoom(RoomVO roomVO) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage deleteRoom(RoomType roomType) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateRoomInfo(RoomVO roomVO) {
        return ResultMessage.SUCCESS;
    }

    @Override
    public List<RoomVO> findRoomsByHotelID(long hotelID) {
        return null;
    }
}
