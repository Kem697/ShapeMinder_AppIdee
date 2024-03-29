package model.data

import com.example.shapeminder_appidee.R
import model.Content
import model.Food

    /*DE:
*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

    /*EN:
* This is my repository which contains the hardcoded content of my app.*/

class AppRepository {

    var content = loadContents()
    var exercises = loadExercises()
    var bodyParts = loadBodyparts()
    var exercisesByBodyparts = loadExercisesByBodypart()
    var groceryCategories = loadGroceryCategories()



    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img,false,false,"",false),
            Content(R.string.title2, R.string.title2,R.drawable.content2_img,false,false,"",false),
            Content(R.string.title3, R.string.title3, R.drawable.content3_img,false,false,"",false),
        )
    }





    fun loadExercises(): List<Content>{
        return listOf(
            Content(R.string.ecBenchpress, R.string.text1, R.drawable.content1_img,true,false,"",false),
            Content(R.string.ecDeadlift, R.string.title2,R.drawable.content2_img,true,false,"",false),
            Content(R.string.ecPullups, R.string.title3, R.drawable.content3_img,true,false,"",false),
            Content(R.string.ecSquats, R.string.text1, R.drawable.content1_img,true,false,"",false),
            Content(R.string.ecShoulderpress, R.string.title2,R.drawable.content1_img,true,false,"",false),

        )
    }


    fun loadBodyparts(): List<Content>{
        return listOf(
            Content(R.string.bpBauch, R.string.text1, R.drawable.bp5abs,true,false,"",false),
            Content(R.string.bpArme, R.string.title2,R.drawable.bp1arms,true,false,"",false),
            Content(R.string.bpSchulter, R.string.title3, R.drawable.bp3shoulders,true,false,"",false),
            Content(R.string.bpRücken, R.string.text1, R.drawable.bp4back,true,false,"",false),
            Content(R.string.bpBeine, R.string.title2,R.drawable.bp2legs,true,false,"",false),
            Content(R.string.bpBrust, R.string.title2,R.drawable.bp6chest,true,false,"",false),

            )
    }


    fun loadExercisesByBodypart(): List<Content>{
        return listOf(
            Content(R.string.weKH_Bicepscurls,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Arme",false),
            Content(R.string.weLH_Bicepscurls,R.string.weLH_Bicepscurls, R.drawable.bp1arms,true,true,"Arme",false),
            Content(R.string.weSZ_Bicepscurls,R.string.weSZ_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Arme",false),
            Content(R.string.weKH_Shoulderpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Schulter",false),
            Content(R.string.we_Pull_ups,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Rücken",false),

            Content(R.string.we_Crunches,R.string.we_CrunchesInstructions, R.drawable.bp1arms,true,true,"Bauch",false),
            Content(R.string.weSquats,R.string.weSquatsInstructions, R.drawable.bp1arms,true,true,"Beine",false),
            Content(R.string.weSZ_Push_ups,R.string.weSZ_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Brust",false),
            Content(R.string.weKH_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust",false),
            Content(R.string.weLH_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust",false),
            Content(R.string.weLH_Incline_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust",false),
            Content(R.string.weKH_Incline_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust",false),
        )
    }


    fun loadGroceryCategories(): List<Food>{
        return listOf(
            Food(R.string.gc_grain_and_corn,R.drawable.foodcat1_noodles_img,"Getreide",true),
            Food(R.string.gc_fruits_and_vegetable,R.drawable.foodcat4_fruits_and_vegetables,"Obst und Gemüse",true),
            Food(R.string.gc_milk_and_eg,R.drawable.foodcat5_milk_and_eggs,"Molkerei und Eier",true),
            Food(R.string.gc_oil_and_fats,R.drawable.foodcat2_oil_img,"Öle und Fette",true),
            Food(R.string.gc_meat_and_fish,R.drawable.foodcat3_meat_img,"Fleisch und Fisch",true),
            Food(R.string.gc_sweets,R.drawable.foodcat6_sweets,"Süssigkeiten",true)
        )
    }






}