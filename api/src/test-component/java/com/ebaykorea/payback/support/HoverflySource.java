package com.ebaykorea.payback.support;

import io.specto.hoverfly.junit.core.SimulationSource;

import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.*;

//TODO
public class HoverflySource {
  public static SimulationSource source = SimulationSource.dsl(
      service(matches("http://order-api-dev.gmarket.co.kr"))
          .get(startsWith("/v1/orders/"))
          .queryParam("fields", any())
          .willReturn(success().body(
              json("{\"orderKey\":\"orderKey\"," +
                  "\"paySeq\":\"38882683\"," +
                  "\"orderBase\":{\"buyerNo\":\"121206632\",\"languageCode\":\"Korean\",\"mobile\":false,\"partnershipCode\":null,\"orderDate\":1669869827.712307}," +
                  "\"buyer\":{\"buyerId\":\"bkmatrix0\",\"buyerNo\":\"121206632\",\"memberType\":\"NormalMember\",\"simpleMember\":null,\"nonMember\":null,\"smileClubMembership\":{\"smileClubMember\":false}}," +
                  "\"orderUnits\":[{\"orderUnitKey\":\"162ddc9cc0400300vncsmgk\",\"orderItem\":{\"snapshotKey\":\"162ccb0d38400200hd2mhgk\",\"itemNo\":\"1100342224\",\"seller\":{\"snapshotKey\":\"162ccb0d4f400200hd2mhgk\",\"sellerNo\":\"100605608\"},\"basePrice\":10000,\"quantity\":1,\"options\":null,\"additions\":null,\"stock\":{},\"branch\":null},\"itemDiscounts\":[],\"coupons\":[],\"smileDeliveryOption\":{\"shippingType\":\"MidNightCutOff\",\"expectArrivalDate\":1669993199,\"cutoffDate\":1669906800}}]," +
                  "\"bundleDiscounts\":[]}")
          ))

          .get("/v1/snapshot/item/items")
          .queryParam("items", any())
          .willReturn(success().body(
              json("[{\"snapshotKey\":\"162ccb0d38400200hd2mhgk\"," +
                  "\"newSnapshot\":false," +
                  "\"dtoHashCode\":0," +
                  "\"itemNo\":\"1100342224\"," +
                  "\"sellerCustNo\":\"100605608\"," +
                  "\"sellerManageValue\":null," +
                  "\"itemLargeCategoryCode\":\"100000038\"," +
                  "\"itemMediumCategoryCode\":\"200000773\"," +
                  "\"itemSmallCategoryCode\":\"300008498\"," +
                  "\"adultCategory\":false," +
                  "\"classCode\":null," +
                  "\"classKind\":null," +
                  "\"minBuyQty\":1," +
                  "\"buyUnitQty\":1," +
                  "\"itemName\":\"더빠른배송+동탄4L+이형N(새벽가능)\"," +
                  "\"itemGlobalName\":null," +
                  "\"itemKind\":100001," +
                  "\"itemEnglishName\":null," +
                  "\"itemOrigin\":\"기타\"," +
                  "\"itemOrigin2\":\"U\"," +
                  "\"discountRate\":null," +
                  "\"dealPrice\":0," +
                  "\"dealPriceKind\":\"  \"," +
                  "\"optionItemDescription\":null," +
                  "\"inventoryType\":null," +
                  "\"inventoryNo\":null," +
                  "\"optionInventoryNo\":null," +
                  "\"optionInventoryType\":null," +
                  "\"templateType\":\"OP\"," +
                  "\"tradeWay\":\"T6\"," +
                  "\"brandName\":null," +
                  "\"makerName\":\"기타\"," +
                  "\"manufactureDate\":null," +
                  "\"attribEndDate\":1639494000," +
                  "\"useType\":null," +
                  "\"buyerMileageRate\":0," +
                  "\"buddyMileageRate\":0," +
                  "\"adultItem\":false," +
                  "\"hasAddedFile\":false," +
                  "\"epin\":0," +
                  "\"brandNo\":0," +
                  "\"g9TradeType\":null," +
                  "\"deliveryGroupNo\":441253826," +
                  "\"minSellOrderNo\":5407161628," +
                  "\"shippingPolicyId\":441253826," +
                  "\"shippingTypeCode\":0," +
                  "\"shippingWeight\":0.6," +
                  "\"donationItem\":null," +
                  "\"buyingLimit\":{\"buyingLimitType\":\"Unknown\",\"buyingLimitQuantity\":0,\"buyingLimitDays\":0,\"buyingMinimumQuantity\":1,\"buyingUnitQuantity\":1}," +
                  "\"itemType\":{\"isECoupon\":false,\"isECouponAuth\":false,\"isMoneyCategory\":false,\"isRental\":false,\"isMount\":false,\"isZeroPrice\":false,\"isAdult\":false,\"isFoodDelivery\":false,\"isSmileDelivery\":true,\"isSmileFresh\":false,\"isExshop\":false,\"isIncomeDuty\":false,\"isSmileClubDeal\":false,\"isSmileClubBizDeal\":false,\"isSmilePayDeal\":false,\"isCartLimited\":false,\"isSFCMall\":false,\"isBusinessMan\":false,\"isUsedItem\":false,\"isInternationalShipping\":false,\"isExceptGlobal\":false,\"isOptionUse\":false,\"isBackwoodsDelivery\":true,\"isSmileBox\":true,\"isTodayDelivery\":false,\"isTaxableItem\":true,\"isAsPossible\":false,\"isG9DupExpose\":false,\"isG9\":false,\"isCashReceiptCategory\":true,\"isDiscountAgreement\":true}," +
                  "\"sdInfo\":{\"sdBrandId\":null,\"sdCategoryCode\":\"00260001000200010000\"}," +
                  "\"legacy\":{\"itemShopKindCode\":\" \",\"itemShopKindCode2\":\"-\",\"itemShopKindCode3\":\"-\"}," +
                  "\"shops\":[]," +
                  "\"options\":[]," +
                  "\"additions\":[]," +
                  "\"purchaseBenefits\":[]," +
                  "\"sellerManageCode\":null," +
                  "\"smileClubJoinBaseDate\":null," +
                  "\"partnershipEstimationType\":null," +
                  "\"partnershipReservationServiceType\":null}]")
          )),

      service(matches("http://payment-api-dev.gmarket.co.kr"))
          .get(startsWith("/v1/payment-records/"))
          .willReturn(success().body(
              json("{\"paymentSequence\":38882683," +
                  "\"txKey\":\"txKey\"," +
                  "\"buyerNo\":\"121206632\"," +
                  "\"buyerId\":\"bkmatrix0\"," +
                  "\"partnershipCode\":\"\"," +
                  "\"mainPaymentMethod\":{\"mediumCode\":\"200000035\",\"smallCode\":\"300000277\",\"amount\":10000,\"paymentAuthorizationSequence\":0}," +
                  "\"subPaymentMethods\":[]," +
                  "\"authentications\":{\"alipay\":null,\"card\":null,\"cellPhone\":null,\"smilePay\":{\"certificationId\":\"1100000017330108S001\",\"smilePayToken\":\"eyJhbGciOiJBMjU2Q0JDIiwiZW5jIjoiQTI1NkNCQyJ9.~xxIpdT9nhO56Id3ARuhLkAalq7nDEzO02jt1JqUnK5x+YnOMudAKudZPehnP8+RzU+anO32mh89kNhqvLO/ncva8S/8Ncsboa+vAe8j8ry5o7fzxS1Z+pxuViCP6pRXrOGmrmrHccpOCZ7+2nWGJJe/HxK9UuZdU0CRvaJafvKfbLjqAlrssr2IROllDmhmzQqPLKQ9eBfTPQfoGpwECi9KRzeiR6yvJGUMiVeYRyffY2+g76itdm4LR6GJgVJNrIkPM/3YFZu7XKlKDNZSJXJtElZiRmVheRqCI7u/qw4HE2RylE/u10pE54LQg7p2VpWySlTk/5fnvnbXW1+/JN40kqYkEw8i35NNMd57+WtQOQcDNlZOGh5g5ETX3dXcoYiJV0fGDbn48nhHFue1JNlaolNIR0x2dRIpJg9wlhWM=\",\"totalMoney\":10000,\"cardRequestMoney\":10000,\"cashRequestMoney\":0,\"mobileRequestMoney\":0,\"etcRequestMoney\":0,\"cashReceiptEncryptionValue\":\"~k7RVYEe698Gi/UoHYTh+1Q==\",\"cashReceiptType\":\"PersonalSeller\",\"cashReceiptWayType\":\"CellPhoneNumber\",\"isFreeInstallment\":false,\"settleGroupSequence\":null,\"smilePayContractCode\":[100],\"smilePayItemType\":1,\"ePrepayRequestMoney\":0},\"paypal\":null,\"smileCash\":null,\"wireTransfer\":null}," +
                  "\"meta\":{\"deviceType\":\"PC\",\"deviceAppType\":\"Web\",\"deviceOsType\":\"Windows\",\"deviceInfo\":\"\",\"deviceBrowserType\":\"Chrome\",\"clientIp\":\"192.168.24.205\",\"serverIp\":\"\",\"sguid\":\"11404003461724000192000000\",\"cguid\":\"31669869615861002512200000\"}}")
          )),

      service(matches("http://transaction-api-dev.gmarket.co.kr"))
          .get(startsWith("/v1/key/maps/txKey"))
          .willReturn(success().body(
              json("{\"txKey\":\"txKey\"," +
                  "\"orders\":[{\"orderUnitKey\":\"162ddc9cc0400300vncsmgk\",\"orderKey\":\"orderKey\",\"txKey\":\"txKey\",\"packNo\":4228017416,\"orderNo\":5408144964,\"contrNo\":2542078143}]," +
                  "\"payments\":null}")
          )),

      service(matches("http://rewardservice-dev.gmarket.co.kr"))
          .post("/api/Read/V2/CashbackReward")
          .body(equalsToJson(json("{\"TotalPrice\":10000,\"Goods\":[{\"Key\":\"5408144964\",\"SiteCd\":0,\"GdNo\":\"1100342224\",\"GdlcCd\":\"100000038\",\"GdmcCd\":\"200000773\",\"GdscCd\":\"300008498\",\"ScNo\":\"100605608\",\"IsSmileClub\":false,\"IsSmileDelivery\":false,\"IsSmileFresh\":false,\"Qty\":1,\"Price\":10000,\"marketabilityItemYn\":\"N\"}]}")))
          .willReturn(success().body(
              json("{\"ReturnBase\":{\"ReturnCode\":\"0000\",\"ReturnValue\":null,\"ErrorMessage\":null}," +
                  "\"Result\":{\"TotalItemCashbackAmount\":0,\"TotalNSPCashbackAmount\":0,\"IfSmileCardCashbackAmount\":200,\"IfNewSmileCardCashbackAmount\":200,\"TotalAutoChargeAmount\":100,\"TotalAutoChargeClubAmount\":200,\"UseEnableDate\":\"2023-01-01\",\"Goods\":[{\"ClubDayExpectSaveAmount\":0,\"ClubDayExpectSaveRate\":0,\"Key\":\"5408144964\",\"GdNo\":\"1100342224\",\"IfSmileClubCashbackAmount\":0,\"CashbackInfo\":[{\"CashbackCd\":2,\"CashbackAmount\":0,\"CashbackSeq\":11224,\"PayType\":\"P\",\"PayRate\":0,\"PayMaxMoney\":0,\"CashbackTitle\":\"스마일페이결제\",\"EtcTitle\":null,\"EtcContent\":null},{\"CashbackCd\":5,\"CashbackAmount\":100,\"CashbackSeq\":11224,\"PayType\":\"A\",\"PayRate\":1,\"PayMaxMoney\":5000,\"CashbackTitle\":\"자동충전결제\",\"EtcTitle\":null,\"EtcContent\":null}],\"ItemCashbackInfo\":{\"ItemAmount\":0,\"SellerAmount\":0,\"CategoryAmount\":0,\"SellerCategoryAmount\":0,\"ShopAmount\":0,\"EtcAmount\":0},\"NSPCashbackInfo\":{\"PayAmount\":0,\"ClubAmount\":0,\"AutoChargeAmount\":100,\"AutoChargeClubAmount\":200},\"ClubDayCashbackInfo\":{\"ClubDay\":null,\"ItemType\":null,\"ClubMemberType\":null,\"ClubDayRate\":0,\"ClubDayMaxAmount\":0,\"ClubDayAmount\":0},\"IfSmileCardT2T3CashbackAmount\":0}]}}")
          ))

          .post("/api/Read/V2/CashbackRewardBackend")
          .body(equalsToJson(json("{\"TotalPrice\":10000,\"Goods\":[{\"Key\":\"5408144964\",\"SiteCd\":0,\"GdNo\":\"1100342224\",\"GdlcCd\":\"100000038\",\"GdmcCd\":\"200000773\",\"GdscCd\":\"300008498\",\"ScNo\":\"100605608\",\"IsSmileClub\":false,\"IsSmileDelivery\":false,\"IsSmileFresh\":false,\"Qty\":1,\"Price\":10000,\"marketabilityItemYn\":\"N\"}]}")))
          .willReturn(success().body(
              json("{\"ReturnBase\":{\"ReturnCode\":\"0000\",\"ReturnValue\":null,\"ErrorMessage\":null}," +
                  "\"Result\":[{\"Key\":\"5408144964\",\"GdNo\":\"1100342224\",\"CashbackSeq\":11224,\"CashbackCode\":2,\"CashbackTitle\":\"\",\"UseEnableDate\":\"2023-01-01\",\"CashbackMoney\":0,\"PayType\":1,\"PayRate\":0,\"PayMaxMoney\":0,\"ChargePayRewardRate\":1,\"ChargePayRewardClubRate\":2,\"ChargePayRewardSmileDeliveryRate\":0,\"ChargePayRewardSmileFreshRate\":0,\"ChargePayRewardSmileDeliveryClubRate\":0,\"ChargePayRewardSmileFreshClubRate\":0,\"ChargePayRewardMaxMoney\":5000,\"ChargePayRewardClubMaxMoney\":5000,\"ChargeCashbackMoney\":100,\"ChargeClubCashbackMoney\":200,\"ClubDay\":\"1111100\",\"ClubDayPayRate\":0,\"ClubDaySaveMaxMoney\":0,\"ClubDayCashbackMoney\":0}]}")
          )),

      service(matches("http://quilt-overmind-dev.gmarket.co.kr"))
          .get(startsWith("/api/smilecash/smileUserKey"))
          .willReturn(success().body(
              json("{\"resultCode\":0," +
                  "\"message\":\"SUCCESS\"," +
                  "\"data\":\"~E1BXAte+hBIBIywxCf2kTwwZWqKpcrP1O7VpT7VwXmcl2Tb/rbaZpeH2VwXtnlyLQJT7wPqmJx1jZjn/IwHnWA==\"}")
          )),

      service(matches("https://clubapi-dev.gmarket.co.kr"))
          .get("/member/gmkt/121206632/synopsis")
          .willReturn(success().body(
              json("{\"ResultCode\":0," +
                  "\"Message\":\"Success\"," +
                  "\"Data\":{\"Member\":{\"IsSmileClubMember\":\"False\",\"UnifyMasterId\":23209519,\"Status\":\"S4\",\"StartDate\":\"2018-05-11T11:22:06\",\"EndDate\":\"\",\"IsToPaidTarget\":\"False\",\"Name\":\"정명환\",\"MemberType\":\"P\",\"MembershipGrade\":\"BASC\",\"PayCycleType\":\"ANNL\",\"PartnerID\":\"S001\",\"IsUnifyMembership\":false,\"MembershipStartDate\":\"\"},\"Benefit\":{\"IsWelcomeGiftOffered\":\"\",\"WelcomeGiftType\":\"UNKNOWN\",\"WelcomeGiftAmount\":0,\"WelcomeGiftInsertDate\":\"\",\"WelcomeGiftOfferDate\":\"\",\"WelcomeGiftOfferExpectDate\":\"\",\"WelcomeGiftRecallDate\":\"\",\"AvailableWelcomeGiftAmount\":35000,\"AvailableWelcomeGiftType\":\"SMILECASH\",\"TotalAmount\":35000},\"Payment\":{\"AnnualFeePayType\":\"UNKNOWN\",\"AnnualFeePayRequestDate\":\"\",\"AnnualFeePayFinishDate\":\"\",\"AnnualFeePayAmount\":0,\"AnnualFeePayStatus\":\"\",\"AnnualFeePayMessage\":\"\",\"IsUsing\":\"\",\"LastAutoOrderDate\":\"\",\"LastAutoOrderAmount\":0,\"UpcomingAutoOrderDate\":\"\",\"UpcomingAutoOrderAmount\":0},\"Subscription\":{\"SubscriptionNo\":0,\"PolicyNo\":0,\"PolicyName\":\"\",\"SubscriptionStartDate\":\"\",\"SubscriptionEndDate\":\"\",\"SubscriptionSubNo\":0,\"SubscriptionSubItem\":\"\",\"SubscriptionSubCost\":0,\"SubscriptionSubStartDate\":\"\",\"SubscriptionSubEndDate\":\"\",\"MembershipGrade\":\"\",\"PayCycleType\":\"\"}}}")
          ))
  );
}
