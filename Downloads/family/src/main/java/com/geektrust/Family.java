package com.geektrust;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.geektrust.FamilyConstants.*;

@Getter
@Builder
@EqualsAndHashCode
public class Family {
    private  RootFamily rootFamily;

    public Family(RootFamily rootFamily) {
        this.rootFamily = rootFamily;

    }

    public void addChild(FamilyMember familyMember) {
        if (familyMember.getFather().equals(rootFamily.getRootFather()) || familyMember.getMother().equals(rootFamily.getRootMother())){
            rootFamily.getRootFather().addChild(familyMember);
            rootFamily.getRootMother().addChild(familyMember);
        }


    }

    public String addChild(String motherName, String childName , String gender) {
        FamilyMember familyMember= searchMember(rootFamily.getRootMother(),motherName);
        if(familyMember==null)
            return PERSON_NOT_FOUND;

        if (familyMember.getGender().equals(MALE))
           return CHILD_ADDITION_FAILED;
        else {
            addChildToFamilyMemberAndSpouse(childName, gender, familyMember);
            return CHILD_ADDITION_SUCCEDED;
        }
    }

    private void addChildToFamilyMemberAndSpouse(String childName, String gender, FamilyMember familyMember) {
        FamilyMember child= new FamilyMember(childName,gender,getParent(familyMember, "Male"),getParent(familyMember, "Female"));
        familyMember.addChild(child);
        familyMember.getSpouse().addChild(child);
    }

    private FamilyMember getParent(FamilyMember familyMember, String gender) {
        return familyMember.getGender().equals(gender) ? familyMember : familyMember.getSpouse();
    }


    public void addSpouse(String name, String spouseName, String gender) {
        FamilyMember rootMother = rootFamily.getRootMother();
        FamilyMember familyMember=null;
        if (rootMother!=null)
         familyMember = searchMember(rootMother,name);
        if (familyMember != null) {
            FamilyMember spouse = new FamilyMember(spouseName, gender, null, null);
            spouse.addSpouse(familyMember);
            familyMember.addSpouse(spouse);
        }
    }

    private FamilyMember searchMember(FamilyMember currentRoot,String memberName) {
        if (memberName == null || currentRoot == null) {
                return null;
            }

            FamilyMember member = null;
            if (memberName.equals(currentRoot.getName())) {
                return currentRoot;
            } else if (currentRoot.getSpouse() != null && memberName.equals(currentRoot.getSpouse().getName())) {
                return currentRoot.getSpouse();
            }

            List<FamilyMember> children = new ArrayList<>();
            if (currentRoot.getGender().equals("Female") && currentRoot.getChildren()!=null) {
                children = currentRoot.getChildren();
            } else if (currentRoot.getSpouse() != null && currentRoot.getSpouse().getChildren()!=null) {
                children = currentRoot.getSpouse().getChildren();
            }

            for (FamilyMember familyMember : children) {
                member = searchMember(familyMember, memberName);
                if (member != null) {
                    break;
                }
            }
            return member;

    }

    public String getRelativeFor(String memberName, String relationship) {
        FamilyMember familyMember = searchMember(rootFamily.getRootMother(), memberName);
        String relations = "";
        if (familyMember==null)
            return PERSON_NOT_FOUND;
        switch (relationship) {

            case DAUGHTER:
                relations = familyMember.searchChildren(FEMALE);
                break;

            case SON:
                relations = familyMember.searchChildren(MALE);
                break;

            case SIBLINGS:
                relations = familyMember.searchSiblings();
                break;

            case SISTER_IN_LAW:
                relations = familyMember.searchInLaws(familyMember, FEMALE);
                break;

            case BROTHER_IN_LAW:
                relations = familyMember.searchInLaws(familyMember, MALE);
                break;

            case MATERNAL_AUNT:
                    relations = familyMember.searchMaternalUncleOrAunt(FEMALE);
                break;

            case PATERNAL_AUNT:
                    relations = familyMember.searchPaternalUncleOrAunt(FEMALE);
                break;

            case MATERNAL_UNCLE:
                    relations = familyMember.searchMaternalUncleOrAunt(MALE);
                break;

            case PATERNAL_UNCLE:
                    relations = familyMember.searchPaternalUncleOrAunt(MALE);
                    break;

            default:
                relations = NOT_YET_IMPLEMENTED;
                break;
        }

            return ("".equals(relations)) ? "NONE" : relations;

    }
}
