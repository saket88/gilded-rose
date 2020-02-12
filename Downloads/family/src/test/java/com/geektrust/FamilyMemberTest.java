package com.geektrust;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FamilyMemberTest extends TestCommons {
    @Test
    public void shouldBeAbleToAddSpouse(){
        Family family = createRootFamily();
        FamilyMember child = addChild(family,"Chita", "Male");
        FamilyMember spouse = new FamilyMember("Amba", "Female", null, null);

        child.addSpouse(spouse);

        assertThat(child.getSpouse(),is(spouse));
        assertThat(spouse.getSpouse(),is(child));

    }

    @Test
    public void shouldBeAbleToAddChild(){
        Family family = createRootFamily();

        FamilyMember child = addChild(family,"Chita", "Male");
        assertThat(family.getRootFamily().getRootFather().getChild(), hasItems(child));
        assertThat(family.getRootFamily().getRootMother().getChild(), hasItems(child));

    }

    @Test
    public void shouldBeAbleToSearchChildrenForAFamilyMember(){
        Family family = createRootFamily();

        FamilyMember chita = addChild(family,"Chita", "Male");
        FamilyMember satya = addChild(family,"Satya", "Female");

        String expectedMale = rootMother.searchChildren("Male");
        String expectedFemale = rootMother.searchChildren("Female");

        assertThat(expectedMale,is(chita.getName()));
        assertThat(expectedFemale,is(satya.getName()));


    }

    @Test
    public void shouldBeAbleToSearchInLaws(){
        Family family = createRootFamily();

        FamilyMember chita = addChild(family,"Chita", "Male");
        FamilyMember ish = addChild(family,"Ish", "Male");
        FamilyMember satya = addChild(family,"Satya", "Female");
        FamilyMember amba = new FamilyMember("Amba", "Female");
        chita.addSpouse(amba);

        String expectedMales = rootMother.searchInLaws(amba,"Male");
        String expectedFemales = rootMother.searchInLaws(amba,"Female");

        assertThat(expectedMales,is(ish.getName()));
        assertThat(expectedFemales,is(satya.getName()));


    }

    @Test
    public void shouldBeAbleToSearchSiblings(){
        Family family = createRootFamily();

        FamilyMember chita = addChild(family,"Chita", "Male");
        FamilyMember ish = addChild(family,"Ish", "Male");
        FamilyMember satya = addChild(family,"Satya", "Female");
        FamilyMember amba = new FamilyMember("Amba", "Female");
        chita.addSpouse(amba);

        String expected = ish.searchSiblings();


       assertThat(expected,is(chita.getName()+" "+satya.getName()));


    }

    @Test
    public void shouldBeAbleToSearchAuntAndUncle(){
        Family family = createRootFamily();

        FamilyMember chita = addChild(family,"Chita", "Male");
        FamilyMember ish = addChild(family,"Ish", "Male");
        FamilyMember satya = addChild(family,"Satya", "Female");
        FamilyMember amba = new FamilyMember("Amba", "Female");
        FamilyMember dritha = new FamilyMember("Dritha", "Female", chita, amba);
        chita.addSpouse(amba);
        amba.addChild(dritha);


        String expectedUncle = dritha.searchPaternalUncleOrAunt("Male");
        String expectedAunt = dritha.searchPaternalUncleOrAunt("Female");


        assertThat(expectedUncle,is(ish.getName()));
        assertThat(expectedAunt,is(satya.getName()));


    }
}
