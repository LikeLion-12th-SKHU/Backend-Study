package com.rommmu.validation;


import jakarta.validation.GroupSequence;

import static com.rommmu.validation.ValidationGroups.*;

@GroupSequence({NotEmptyGroup.class, NotBlankGroup.class, SizeGroup.class, MinGroup.class, PatternGroup.class})
public interface ValidationSequence {
}
