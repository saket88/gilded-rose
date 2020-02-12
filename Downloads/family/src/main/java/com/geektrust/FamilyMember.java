package com.geektrust;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class FamilyMember {
    private final String name;
    private final String gender;
    private  FamilyMember father;
    private  FamilyMember mother;
    private List<FamilyMember> children;
    private FamilyMember spouse;

    public FamilyMember(String name, String gender, FamilyMember father, FamilyMember mother) {
        this.name= name;
        this.gender=gender;
        this.father=father;
        this.mother=mother;
        children = new ArrayList<>();
    }

    public FamilyMember(String name, String gender) {
        this.name=name;
        this.gender = gender;
        children = new ArrayList<>();
    }

    public List<FamilyMember> getChild() {
        return new ArrayList<FamilyMember>(this.children);
    }

    public void addChild(FamilyMember familyMember) {
        if (children!=null)
        this.children.add(familyMember);
    }

    public void addSpouse(FamilyMember spouse) {
        this.spouse = spouse;
        if (spouse.getSpouse()==null)
            spouse.addSpouse(this);
    }

    public String searchChildren(String gender) {
       return this.getChildren()
                .stream()
                .filter(familyMember -> familyMember.getGender().equals(gender))
                .map(FamilyMember::getName)
                .collect(Collectors.joining(" "));
    }
    public String searchChildrenOtherThan(String name ,String gender) {
        return this.getChildren()
                .stream()
                .filter(familyMember -> familyMember.getGender().equals(gender))
                .filter(member -> !member.getName().equals(name))
                .map(FamilyMember::getName)
                .collect(Collectors.joining(" "));
    }

    public String searchInLaws(FamilyMember member, String gender) {
        StringBuilder inLaws = new StringBuilder("");
        spouseFamilyInLaws(member, gender, inLaws);
        ownFamilyInLaws(member, gender, inLaws);
        return inLaws.toString();


    }

    private void ownFamilyInLaws(FamilyMember member, String gender, StringBuilder inLaws) {
        if (member.getMother()!=null)
            inLaws.append(member.getMother().getChildren()
                    .stream()
                    .filter(member1 -> member1.getSpouse()!=null)
                    .filter(member1 -> member1.getSpouse().getGender().equals(gender))
                    .map(member1 ->member1.getSpouse().getName())
                    .collect(Collectors.joining(" ")));
    }

    private void spouseFamilyInLaws(FamilyMember member, String gender, StringBuilder inLaws) {
        if (member.getSpouse() != null && member.getSpouse().getMother() != null) {
            inLaws.append(member.getSpouse().getMother().searchChildrenOtherThan(member.getSpouse().getName(),gender));
        }
    }

    public String searchSiblings() {
       return this.getMother()
               .getChildren()
               .stream()
               .filter(member -> !member.getName().equals(this.getName()))
               .map(FamilyMember::getName)
               .collect(Collectors.joining(" "));
    }

    public String searchMaternalUncleOrAunt(String gender) {
        return this.getMother()
                .getMother()
                .getChildren()
                .stream()
                .filter(member -> !member.getName().equals(this.getMother().getName()))
                .filter(member -> member.getGender().equals(gender))
                .map(FamilyMember::getName)
                .collect(Collectors.joining(" "));
    }

    public String searchPaternalUncleOrAunt(String gender) {
        if (this.getFather().getMother()==null)
            return "";
        return this.getFather()
                .getMother()
                .getChildren()
                .stream()
                .filter(member -> !member.getName().equals(this.getFather().getName()))
                .filter(member -> member.getGender().equals(gender))
                .map(FamilyMember::getName)
                .collect(Collectors.joining(" "));
    }
}
