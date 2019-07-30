package com.medlife.deliveries.clickpost.enums;

import java.util.HashMap;
import java.util.Map;

public enum SubPartner {

    AMBE_EXPRESS(45L, true, false, true),
    ARAMEX(2L, true, true, true),
    AUM_LOGISTIC(67L, false, true, true),
    AUPOST(51L, true, false, false),
    BIKXIE(52L, false, false, true),
    BIZONGO(46L, true, false, true),
    BLUEDART(5L, true, true, true),
    BLUEDART_REVERSE(28L, true, true, true),
    BOOK_MY_PACKET(23L, true, false, true),
    CRITICAL_LOG(58L, false, false, true),
    DELHIVERY(4L, true, true, true),
    DELHIVERY_B2B(66L, false, false, true),
    DELHIVERY_REVERSE(25L, true, false, true),
    DELHIVERY_WITHOUT_CREDENTIALS_TRACKING(57L, false, false, true),
    DHL(49L, true, false, true),
    DOTZOT(22L, true, false, true),
    DTTDC(8L, true, false, true),
    ECOM_EXPRESS(3L, true, true, true),
    ECOM_EXPRESS_REVERSE(24L, true, false, true),
    EKART_LOGISTICS(55L, true, false, true),
    EKART_REVERSE(64L, true, false, true),
    ESSKAY(48L, true, false, true),
    FAREYE(31L, true, false, true),
    FAREYE_REVERSE(38L, true, false, true),
    FEDEX(1L, true, true, true),
    FUTURE_SUPPLY_CHAIN(68L, false, false, true),
    GATI(32L, true, false, true),
    GATI_B2B(71L, true, false, true),
    GMS_LOGISTIC(65L, false, false, true),
    GOPIGEON(13L, true, false, true),
    HOLISOL(15L, true, false, true),
    INDIA_POST(1001L, true, false, true),
    INNOV_EX(10L, true, false, true),
    JV_EXPRESS(70L, true, false, true),
    KERRY_INDEV(72L, false, false, true),
    LOADSHARE(50L, true, false, true),
    LOGISTIFY(73L, true, false, true),
    MILE_REACH(53L, false, false, true),
    NUVOEX_FORWARD(17L, false, false, true),
    NUVOEX_REVERSE(7L, true, false, true),
    ONLINE_EXPRESS(12L, false, false, true),
    PICKRR(30L, true, false, true),
    POSTA_KENYA(40L, false, false, true),
    RAPID_DELIVERY(42L, true, false, true),
    RBL(59L, true, false, true),
    RIVIGO(56L, false, false, true),
    SAFEX(26L, true, false, true),
    SAKSHAM(27L, true, false, true),
    SECURA_EX(47L, true, false, true),
    SELF(35L, true, false, true),
    SELF_REVERSE(34L, true, false, true),
    SEQUEL(29L, true, false, true),
    SHADOWFAX_FORWARD(9L, true, true, true),
    SHADOWFAX_REVERSE(11L, true, false, true),
    SHIPBAZAR(19L, true, false, true),
    SHIPDELIGHT(16L, true, false, true),
    SHREE_MARUTI_COURIER(69L, false, false, true),
    SKYNET(44L, false, false, true),
    SMARSHIP(60L, false, false, true),
    SPOT_ON(39L, true, false, true),
    ST_COURIER(62L, true, false, true),
    TCI(36L, true, false, true),
    VAMASHIP(21L, true, false, true),
    VAMASHIP_INTERNATIONAL(43L, true, false, true),
    WADI_LASTMILE(33L, false, false, true),
    WOW_EXPRESS(14L, true, true, true),
    XPRESS_BEES(6L, true, true, true),
    XPRESS_BEES_CARGO(61L, true, false, true),
    XPRESS_BEES_P2P(54L, false, false, true),
    XPRESS_BEES_REVERSE(41L, true, false, true),
    YAKIT(20L, true, false, true),
    ZODIAC(18L, false, false, true);


    private Long subPartnerId;

    private Boolean isOrderCreationEnabled;

    private Boolean isOrderCancellationEnabled;

    private Boolean isOrderTrackingEnabled;

    SubPartner(Long  subPartnerId,
               Boolean isOrderCreationEnabled,
               Boolean isOrderCancellationEnabled,
               Boolean isOrderTrackingEnabled)
    {
        this.subPartnerId = subPartnerId;
        this.isOrderCancellationEnabled = isOrderCancellationEnabled;
        this.isOrderCreationEnabled =  isOrderCreationEnabled;
        this.isOrderTrackingEnabled = isOrderTrackingEnabled;
    }

    public Long id(){
        return this.subPartnerId;
    }

    public Boolean isOrderCreationEnabled(){
        return this.isOrderCreationEnabled;
    }

    public Boolean isOrderCancellationEnabled(){
        return this.isOrderCancellationEnabled;
    }

    public Boolean isOrderTrackingEnabled(){
        return this.isOrderTrackingEnabled;
    }

    private static final Map<Long, SubPartner> lookup = new HashMap<>();

    static {
        for (SubPartner c : SubPartner.values()) {
            lookup.put(c.id(), c);
        }
    }

    public static SubPartner get(Long id) {
        return lookup.get(id);
    }
}
