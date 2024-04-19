package model.data.local
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shapeminder_appidee.R
import model.data.local.model.Content
import model.data.local.model.FoodFinderCategory
import model.data.local.model.TrainingsSession
import java.util.Locale

    /*DE:
*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

    /*EN:
* This is my repository which contains the hardcoded content of my app.*/


class LocalRepository (private val trainingDatabase: TrainingSessionsDatabase) {

    val trainingSessionList: LiveData<List<TrainingsSession>> = trainingDatabase.trainingsSessionDao.getAll()

    suspend fun insertNewTrainingSession(newTrainingsSession: TrainingsSession){
        try {
            trainingDatabase.trainingsSessionDao.insertSession(newTrainingsSession)
        } catch (e: Exception){
            var tag = "Lokaler Repo??"
            Log.e(tag,"Fehler beim Einsetzen der neuen Trainingseinheit in die Datenbank!!")
        }
    }

    var content = loadContents()
    var exercises = loadExercises()
    var bodyParts = loadBodyparts()
    var exercisesByBodyparts = loadExercisesByBodypart()
    var groceryCategories = loadGroceryCategories()



    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img,false,false,"",false,null,null),
            Content(R.string.title2, R.string.title2,R.drawable.content2_img,false,false,"",false,null,null),
            Content(R.string.title3, R.string.title3, R.drawable.content3_img,false,false,"",false,null,null),
        )
    }





    fun loadExercises(): List<Content>{
        return listOf(
            Content(R.string.ecBenchpress, R.string.text1, R.drawable.content1_img,true,false,"",false,null,null),
            Content(R.string.ecDeadlift, R.string.title2,R.drawable.content2_img,true,false,"",false,null,null),
            Content(R.string.ecPullups, R.string.title3, R.drawable.content3_img,true,false,"",false,null,null),
            Content(R.string.ecSquats, R.string.text1, R.drawable.content1_img,true,false,"",false,null,null),
            Content(R.string.ecShoulderpress, R.string.title2,R.drawable.content1_img,true,false,"",false,null,null),

        )
    }


    fun loadBodyparts(): List<Content>{
        return listOf(
            Content(R.string.bpBauch, R.string.text1, R.drawable.bp5abs,true,false,"",false,null,null),
            Content(R.string.bpArme, R.string.title2,R.drawable.bp1arms,true,false,"",false,null,null),
            Content(R.string.bpSchulter, R.string.title3, R.drawable.bp3shoulders,true,false,"",false,null,null),
            Content(R.string.bpRücken, R.string.text1, R.drawable.bp4back,true,false,"",false,null,null),
            Content(R.string.bpBeine, R.string.title2,R.drawable.bp2legs,true,false,"",false,null,null),
            Content(R.string.bpBrust, R.string.title2,R.drawable.bp6chest,true,false,"",false,null,null),

            )
    }


    fun loadExercisesByBodypart(): List<Content>{
        return listOf(
            Content(R.string.weKH_Bicepscurls,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Arme",false,R.string.weKH_BicepscurlsYtVideo,false),
            Content(R.string.weLH_Bicepscurls,R.string.weLH_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Arme",false,R.string.weLH_BicepscurlsYtVideo,false),
            Content(R.string.weSZ_Bicepscurls,R.string.weSZ_BicepscurlsInstructions, R.drawable.bp1arms,true,true,"Arme",false,R.string.weSZ_BizepscurlsYtVideo,false),
            Content(R.string.weKH_Shoulderpress,R.string.weKH_ShoulderpressInstructions, R.drawable.bp1arms,true,true,"Schulter",false,R.string.weKH_ShoulderpressYtVideo,false),
            Content(R.string.weKH_SideRaise,R.string.weKH_SideRaiseInstructions, R.drawable.bp1arms,true,true,"Schulter",false,R.string.weKH_SideRaiseYtVideo,false),
            Content(R.string.weKH_SideRaiseLayOnBench,R.string.weKH_SideRaiseLayOnBenchInstruction, R.drawable.bp1arms,true,true,"Schulter",false,R.string.weKH_SideRaiseLayOnBenchYtVideo,false),
            Content(R.string.we_Pull_ups,R.string.weKH_BicepscurlsInstruction, R.drawable.bp1arms,true,true,"Rücken",false,null,false),
            Content(R.string.weCable_Row,R.string.weCable_RowInstructions, R.drawable.bp1arms,true,true,"Rücken",false,R.string.weCable_RowYtVideo,false),

            Content(R.string.we_Crunches,R.string.we_CrunchesInstructions, R.drawable.bp1arms,true,true,"Bauch",false,null,false),
            Content(R.string.we_RisingLegs,R.string.we_RisingLegsInstructions, R.drawable.bp1arms,true,true,"Bauch",false,R.string.we_Rising_LegsYtVideo,false),
            Content(R.string.weLH_Squats,R.string.weSquatsInstructions, R.drawable.bp1arms,true,true,"Beine",false,null,false),
            Content(R.string.we_Lunges,R.string.we_LungesInstruction, R.drawable.bp1arms,true,true,"Beine",false,R.string.we_LungesYtVideo,false),
            Content(R.string.weSZ_Push_ups,R.string.wePush_upsInstructions, R.drawable.bp1arms,true,true,"Brust",false,null,false),
            Content(R.string.weKH_Benchpress,R.string.weKH_BenchpressInstructions, R.drawable.bp1arms,true,true,"Brust",false,R.string.weKH_BenchpressYtVideo,false),
            Content(R.string.weLH_Benchpress,R.string.weLH_BenchpressInstructions, R.drawable.bp1arms,true,true,"Brust",false,R.string.weLH_BenchpressYtVideo,false),
            Content(R.string.weLH_Incline_Benchpress,R.string.weLH_InclineBenchpressInstructions, R.drawable.bp1arms,true,true,"Brust",false,R.string.weLH_InclineBenchpressYtVideo,false),
            Content(R.string.weKH_Incline_Benchpress,R.string.weKH_InclineBenchpressInstructions, R.drawable.bp1arms,true,true,"Brust",false,R.string.weKH_InclineBenchpressYtVideo,false),
            Content(R.string.we_Dips,R.string.we_DipsInstructions, R.drawable.bp1arms,true,true,"Brust",false,null,false),
        )
    }



    fun loadGroceryCategories(): List<FoodFinderCategory>{
        return listOf(
            FoodFinderCategory(R.string.gc_grain_and_corn,R.drawable.foodcat1_noodles_img,"Getreide",true),
            FoodFinderCategory(R.string.gc_fruits_and_vegetable,R.drawable.foodcat4_fruits_and_vegetables,"Obst und Gemüse",true),
            FoodFinderCategory(R.string.gc_milk_and_eg,R.drawable.foodcat5_milk_and_eggs,"Molkerei und Eier",true),
            FoodFinderCategory(R.string.gc_oil_and_fats,R.drawable.foodcat2_oil_img,"Öle und Fette",true),
            FoodFinderCategory(R.string.gc_meat_and_fish,R.drawable.foodcat3_meat_img,"Fleisch und Fisch",true),
            FoodFinderCategory(R.string.gc_sweets,R.drawable.foodcat6_sweets,"Süssigkeiten",true)
        )
    }


  /*  fun loadTrainingSessions(): List<TrainingsSession> {
        return listOf(
            TrainingsSession()
        )
    }*/

}