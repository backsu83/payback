package com.ebaykorea.payback.core.domain.entity.order;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static com.ebaykorea.payback.util.PaybackCollections.isEmpty;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.List;
import java.util.Map;

import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.Value;

@Value
public class ItemSnapshots {

  List<ItemSnapshot> itemSnapshots;

  public static ItemSnapshots of(final List<ItemSnapshot> itemSnapshots) {
    return new ItemSnapshots(itemSnapshots);
  }

  public Map<String, ItemSnapshot> bySnapshotKey() {
    return itemSnapshots.stream().collect(toUnmodifiableMap(ItemSnapshot::getSnapshotKey, identity()));
  }

  private ItemSnapshots(final List<ItemSnapshot> itemSnapshots) {
    this.itemSnapshots = itemSnapshots;

    validate();
  }

  // 불변식
  private void validate() {
    if (isEmpty(itemSnapshots)) {
      throw new PaybackException(DOMAIN_ENTITY_001, "itemSnapshots 정보가 없습니다");
    }
  }
}
