package com.rommmu.validation;


import jakarta.validation.GroupSequence;

import static com.rommmu.validation.ValidationGroups.*;

@GroupSequence({NotEmptyGroup.class, NotBlankGroup.class, PatternGroup.class, SizeGroup.class, MinGroup.class})
public interface ValidationSequence {
}
