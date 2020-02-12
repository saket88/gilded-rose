package com.geektrust;

public class TestCommons {

    protected FamilyMember rootFather;
    protected FamilyMember rootMother;
    protected Family family;

    protected Family createRootFamily() {
        rootFather = new FamilyMember("Shan", "Male", null, null);
        rootMother = new FamilyMember("Anga", "Female", null, null);

        return new  Family(new RootFamily(rootFather, rootMother));
    }

    protected FamilyMember addChild(Family family, String name, String gender) {
        FamilyMember child = new FamilyMember( name,gender,rootFather, rootMother);
        family.addChild(child);
        return child;
    }
}
