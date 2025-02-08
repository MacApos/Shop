package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.Create;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, Create.class})
public interface DefaultAndCreateOrUpdate {
}
