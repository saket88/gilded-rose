package com.geektrust;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Paths;

import static com.geektrust.FamilyConstants.CHILD_ADDITION_SUCCEDED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FamilyTest extends TestCommons {



    @BeforeAll
     void setUp()  {
        Application application = new Application();
        String pathInitFile = Paths.get("src/main/resources/construct_family.txt").toAbsolutePath().toString();
        application.processfile(pathInitFile);
        family = application.getFamily();
    }


    @Test
    public void thereShouldBeAFamilyMemberWithHead(){
        Family family = createRootFamily();
        assertThat(family.getRootFamily().getRootFather(),is(rootFather));
        assertThat(family.getRootFamily().getRootFather(),is(rootFather));
        assertThat(family.getRootFamily().getRootMother(),is(rootMother));

    }
    @Test
    public void thereShouldBeAFamilyMemberWithHead_v2(){
        Family family = createRootFamily();

        String added= family.addChild( "Anga","Janki","Female" );
        assertThat( CHILD_ADDITION_SUCCEDED, is( added ));

    }

    @Test
    void shouldAbleToAddAChildToFatherOnlyWhenHeIsAdded() {
        rootFather = new FamilyMember("Shan", "Male", null, null);

        rootMother = new FamilyMember("Anga", "Female", null, null);
        rootFather.addSpouse(  rootMother);
        rootMother.addSpouse( rootFather );
        Family family= new  Family(new RootFamily(rootFather, rootMother));

        String addChild = family.addChild( "Shan", "Ravi", "Male" );
        assertThat( addChild,is(  CHILD_ADDITION_SUCCEDED ));

    }







    @Test
    public void shouldBeAbleToAddChildUsingMothersName(){
        Family family = createRootFamily();
        FamilyMember spouse = new FamilyMember("Amba", "Female", null, null);
        addChild(family,"Chita", "Male");
        family.getRootFamily().getRootMother().getChild().get(0).addSpouse(spouse);

        family.addChild("Amba","Dhriti","Female");

       ;
        assertThat(family.getRootFamily().getRootFather().getChildren().get(0).getChildren().get(0).getName(), is("Dhriti"));
        assertThat(family.getRootFamily().getRootMother().getChildren().get(0).getChildren().get(0).getName(), is("Dhriti"));

    }



    @ParameterizedTest
    @CsvSource({
            "Amba,Sister-In-Law,Satya",
            "Satvy,Brother-In-Law,Vyas",
            "Atya,Sister-In-Law,Satvy Krpi",
            "Queen Anga,Son,Chit Ish Vich Aras",
            "Queen Anga,Daughter,Satya",
            "Chit,Siblings,Ish Vich Aras Satya",
            "Jnki,HUSBAND,NOT YET IMPLEMENTED",
            "Satya,WIFE,NOT YET IMPLEMENTED",
            "Satya,Paternal-Uncle,NONE",
            "Kriya,Paternal-Uncle,Asva",
            "Asva,Maternal-Uncle,Chit Ish Vich Aras",
            "Tritha,Paternal-Aunt,Satya",
            "Yodhan,Maternal-Aunt,Tritha",

    })
    public void getRelationshipValuesFromCsvFile(String memberName, String relation, String expected) {
        String actual = family.getRelativeFor(memberName, relation);
        assertEquals(expected, actual);
    }


}
