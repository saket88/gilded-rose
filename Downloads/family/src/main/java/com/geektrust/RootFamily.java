package com.geektrust;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class RootFamily {


    private final FamilyMember rootFather;
    private final FamilyMember rootMother;

    public RootFamily(FamilyMember rootFather, FamilyMember rootMother) {
        this.rootFather=rootFather;
        this.rootMother=rootMother;
    }
}
