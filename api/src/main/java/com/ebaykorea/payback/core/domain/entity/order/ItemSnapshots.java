package com.ebaykorea.payback.core.domain.entity.order;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.List;
import java.util.Map;
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
}
