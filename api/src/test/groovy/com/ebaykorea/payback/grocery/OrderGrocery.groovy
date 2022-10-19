package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.entity.cashback.buyer.Buyer
import com.ebaykorea.payback.core.domain.entity.order.BundleDiscount
import com.ebaykorea.payback.core.domain.entity.order.BundleDiscountUnit
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot
import com.ebaykorea.payback.core.domain.entity.order.Order
import com.ebaykorea.payback.core.domain.entity.order.OrderItem
import com.ebaykorea.payback.core.domain.entity.order.OrderItemAddition
import com.ebaykorea.payback.core.domain.entity.order.OrderItemOption
import com.ebaykorea.payback.core.domain.entity.order.OrderUnit
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitCoupon
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitDiscount

import java.time.Instant

class OrderGrocery {
  static def Order_생성(Map map = [:]) {
    new Order(
        (map.orderKey ?: "orderKey") as String,
        (map.paySeq ?: 1L) as Long,
        (map.buyer ?: Buyer_생성(map)) as Buyer,
        (map.orderDate ?: Instant.parse("2022-10-17T09:35:24.00Z")) as Instant,
        (map.orderUnits ?: [OrderUnit_생성(map)]) as List<OrderUnit>,
        (map.bundleDiscounts ?: null) as List<BundleDiscount>,
        (map.tenant ?: "gmarket") as String
    )
  }

  static def Buyer_생성(Map map = [:]) {
    new Buyer(
        (map.buyerNo ?: "buyerNo") as String,
        (map.buyerId ?: "buyerId") as String,
        (map.userKey ?: "userKey") as String,
        (map.member) as boolean,
        (map.smileClubMember ?: false) as boolean
    )
  }

  static def OrderUnit_생성(Map map = [:]) {
    new OrderUnit(
        (map.orderUnitKey ?: "orderUnitKey1") as String,
        (map.orderItem ?: OrderItem_생성(map)) as OrderItem,
        (map.itemDiscounts ?: []) as List<OrderUnitDiscount>,
        (map.coupons ?: []) as List<OrderUnitCoupon>,
    )
  }

  static def OrderItem_생성(Map map = [:]) {
    new OrderItem(
        (map.itemSnapshotKey ?: "itemSnapshotKey1") as String,
        (map.itemNo ?: "itemNo1") as String,
        (map.basePrice ?: 1000L) as BigDecimal,
        (map.quantity ?: 1) as int,
        (map.options ?: []) as List<OrderItemOption>,
        (map.additions ?: []) as List<OrderItemAddition>,
        (map.branchPrice ?: null) as BigDecimal
    )
  }

  static def OrderItemOption_생성(Map map = [:]) {
    new OrderItemOption(
        (map.optionNo ?: 1L) as long,
        (map.sellPrice ?: 100L) as BigDecimal,
    )
  }

  static def OrderItemAddition_생성(Map map = [:]) {
    new OrderItemAddition(
        (map.additionNo ?: 1L) as long,
        (map.quantity ?: 1L) as Integer,
        (map.sellPrice ?: 100L) as BigDecimal,
    )
  }

  static def OrderUnitDiscount_생성(Map map = [:]) {
    new OrderUnitDiscount(
        (map.snapshotKey ?: "discountSnapshotKey1") as String,
        (map.discountAmount ?: 100L) as BigDecimal,
    )
  }

  static def OrderUnitCoupon_생성(Map map = [:]) {
    new OrderUnitCoupon(
        (map.snapshotKey ?: "couponSnapshotKey1") as String,
        (map.couponAmount ?: 100L) as BigDecimal,
    )
  }

  static def BundleDiscount_생성(Map map = [:]) {
    new BundleDiscount(
        (map.snapshotKey ?: "bundleDiscountSnapshotKey1") as String,
        (map.bundleDiscountUnits ?: [BundleDiscountUnit_생성(map)]) as List<BundleDiscountUnit>,
    )
  }

  static def BundleDiscountUnit_생성(Map map = [:]) {
    new BundleDiscountUnit(
        (map.orderUnitKey ?: "orderUnitKey1") as String,
        (map.discountAmount ?: 100L) as BigDecimal,
    )
  }

  static def ItemSnapshot_생성(Map map = [:]) {
    new ItemSnapshot(
        (map.snapshotKey ?: "itemSnapshotKey1") as String,
        (map.itemNo ?: "itemNo1") as String,
        (map.sellerCustNo ?: "sellerCustNo") as String,
        (map.itemLargeCategoryCode ?: "1") as String,
        (map.itemMediumCategoryCode ?: "2") as String,
        (map.itemSmallCategoryCode ?: "3") as String,
        (map.isMoneyCategory ?: false) as boolean,
        (map.isSmileDelivery ?: false) as boolean,
        (map.buyerMileageRate ?: 0L) as BigDecimal
    )
  }
}
