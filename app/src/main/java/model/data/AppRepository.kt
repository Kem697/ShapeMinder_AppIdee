package model.data

import com.example.shapeminder_appidee.R
import model.Content
import model.Food

/*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

class AppRepository {

    var content = loadContents()
    var exercises = loadExercises()
    var bodyParts = loadBodyparts()
    var exercisesByBodyparts = loadExercisesByBodypart()
    var groceryCategories = loadGroceryCategories()



    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img,false,false,""),
            Content(R.string.title2, R.string.title2,R.drawable.content2_img,false,false,""),
            Content(R.string.title3, R.string.title3, R.drawable.content3_img,false,false,""),
        )
    }





    fun loadExercises(): List<Content>{
        return listOf(
            Content(R.string.ecBenchpress, R.string.text1, R.drawable.content1_img,true,false,""),
            Content(R.string.ecDeadlift, R.string.title2,R.drawable.content2_img,true,false,""),
            Content(R.string.ecPullups, R.string.title3, R.drawable.content3_img,true,false,""),
            Content(R.string.ecSquats, R.string.text1, R.drawable.content1_img,true,false,""),
            Content(R.string.ecShoulderpress, R.string.title2,R.drawable.content1_img,true,false,""),

        )
    }


    fun loadBodyparts(): List<Content>{
        return listOf(
            Content(R.string.bpBauch, R.string.text1, R.drawable.bp5abs,true,false,""),
            Content(R.string.bpArme, R.string.title2,R.drawable.bp1arms,true,false,""),
            Content(R.string.bpSchulter, R.string.title3, R.drawable.bp3shoulders,true,false,""),
            Content(R.string.bpRücken, R.string.text1, R.drawable.bp4back,true,false,""),
            Content(R.string.bpBeine, R.string.title2,R.drawable.bp2legs,true,false,""),
            Content(R.string.bpBrust, R.string.title2,R.drawable.bp6chest,true,false,""),

            )
    }


    fun loadExercisesByBodypart(): List<Content>{
        return listOf(
            Content(R.string.weKH_Bicepscurls,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Arme"),
            Content(R.string.weLH_Bicepscurls,R.string.weLH_Bicepscurls, R.drawable.bp1arms,true,true,"Arme"),
            Content(R.string.weSZ_Bicepscurls,R.string.weSZ_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Arme"),
            Content(R.string.weKH_Shoulderpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Schulter"),
            Content(R.string.we_Pull_ups,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Rücken"),

            Content(R.string.we_Crunches,R.string.we_CrunchesInstructions, R.drawable.bp1arms,true,true,"Bauch"),
            Content(R.string.weSquats,R.string.weSquatsInstructions, R.drawable.bp1arms,true,true,"Beine"),
            Content(R.string.weSZ_Push_ups,R.string.weSZ_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Brust"),
            Content(R.string.weKH_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust"),
            Content(R.string.weLH_Benchpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Brust"),
        )
    }


    fun loadGroceryCategories(): List<Food>{
        return listOf(
            Food(R.string.gc_grain_and_corn,R.drawable.content3_img,"Getreide",true),
            Food(R.string.gc_fruits_and_vegetable,R.drawable.content3_img,"Obst und Gemüse",true),
            Food(R.string.gc_milk_and_eg,R.drawable.content3_img,"Molkerei und Eier",true),
            Food(R.string.gc_oil_and_fats,R.drawable.content3_img,"Öle und Fette",true),
            Food(R.string.gc_meat_and_fish,R.drawable.content3_img,"Fleisch und Fisch",true),
            Food(R.string.gc_sweets,R.drawable.content3_img,"Süssigkeiten",true)
        )
    }






}