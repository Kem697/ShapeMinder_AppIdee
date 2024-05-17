package model.data.local
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shapeminder_appidee.R
import model.data.local.model.myTraining.Bodypart
import model.data.local.model.myTraining.Content
import model.data.local.model.myTraining.Exercise
import model.data.local.model.myTraining.TrainingsSession

/*DE:
*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

    /*EN:
* This is my repository which contains the hardcoded content of my app.*/


class LocalRepository (private val trainingDatabase: TrainingSessionsDatabase) {



    val trainingSessionList: LiveData<List<TrainingsSession>> = trainingDatabase.trainingsSessionDao.getAll()

    var content = loadContents()
    var bodyParts = loadBodyparts()
    var allExercisesByBodyparts = loadExercisesByBodypart()





    suspend fun insertNewTrainingSession(newTrainingsSession: TrainingsSession){
        try {
            trainingDatabase.trainingsSessionDao.insertSession(newTrainingsSession)
        } catch (e: Exception){
            var tag = "Lokaler Repo??"
            Log.e(tag,"Fehler beim Einsetzen der neuen Trainingseinheit in die Datenbank!! $e")
        }
    }



    suspend fun updateTrainingsession(currentSession: TrainingsSession){
        try {
            trainingDatabase.trainingsSessionDao.updateSession(currentSession)
        }catch (e: Exception){
            var tag = "Lokaler Repo??"
            Log.e(tag,"Fehler beim Aktualisieren der Trainingseinheit in der Datenbank!! $e")
        }
    }


    suspend fun deleteTrainingsession(currentSession: TrainingsSession){
        try {
            trainingDatabase.trainingsSessionDao.deleteSession(currentSession)
        }catch (e: Exception){
            var tag = "Lokaler Repo??"
            Log.e(tag,"Fehler beim Löschen der Trainingseinheit aus der Datenbank!! $e")
        }
    }


    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img),
            Content(R.string.title2, R.string.text2,R.drawable.content2_img),
            Content(R.string.title3, R.string.text3, R.drawable.content3_img),
        )
    }



    fun loadBodyparts(): List<Bodypart>{
        return listOf(
            Bodypart(R.string.bpBauch, R.drawable.bp5_abs_silhouette,true),
            Bodypart(R.string.bpBiceps, R.drawable.bp1_biceps_silhouette_png,true),
            Bodypart(R.string.bpSchulter, R.drawable.bp3_shoulders_silhouette_png,true),
            Bodypart(R.string.bpRücken,  R.drawable.bp4_back_silhoette,true),
            Bodypart(R.string.bpBeine, R.drawable.bp2_legs_silhouette_png,true),
            Bodypart(R.string.bpBrust,R.drawable.bp6_chest_silhouette,true)
            )
    }


    fun loadExercisesByBodypart(): List<Exercise>{
        return listOf(
            Exercise(R.string.weKH_Bicepscurls,R.string.weKH_BicepscurlsInstruction, R.drawable.ex_kh_bicepscurls,true,true,R.string.bpBiceps,false,R.string.weKH_BicepscurlsYtVideo,false),
            Exercise(R.string.weLH_Bicepscurls,R.string.weLH_BicepscurlsInstructions, R.drawable.ex_lh_bicepscurls,true,true,R.string.bpBiceps,false,R.string.weLH_BicepscurlsYtVideo,false),
            Exercise(R.string.weSZ_Bicepscurls,R.string.weSZ_BicepscurlsInstructions, R.drawable.ex_sz_bicepscurls,true,true,R.string.bpBiceps,false,R.string.weSZ_BizepscurlsYtVideo,false),
            Exercise(R.string.weKH_Shoulderpress,R.string.weKH_ShoulderpressInstructions, R.drawable.ex_kh_shoulderpress,true,true,R.string.bpSchulter,false,R.string.weKH_ShoulderpressYtVideo,false),
            Exercise(R.string.weKH_SideRaise,R.string.weKH_SideRaiseInstructions, R.drawable.ex_kh_side_rise,true,true,R.string.bpSchulter,false,R.string.weKH_SideRaiseYtVideo,false),
            Exercise(R.string.weKH_SideRaiseLayOnBench,R.string.weKH_SideRaiseLayOnBenchInstruction, R.drawable.ex_kh_reverse_side_rise,true,true,R.string.bpSchulter,false,R.string.weKH_SideRaiseLayOnBenchYtVideo,false),
            Exercise(R.string.we_Pull_ups,R.string.weKH_BicepscurlsInstruction, R.drawable.bp4back,true,true,R.string.bpRücken,false,null,false),
            Exercise(R.string.weCable_Row,R.string.weCable_RowInstructions, R.drawable.ex_ma_cable_rows,true,true,R.string.bpRücken,false,R.string.weCable_RowYtVideo,false),

            Exercise(R.string.we_Crunches,R.string.we_CrunchesInstructions, R.drawable.bp5abs,true,true,R.string.bpBauch,false,null,false),
            Exercise(R.string.we_RisingLegs,R.string.we_RisingLegsInstructions, R.drawable.ex_rise_legs,true,true,R.string.bpBauch,false,R.string.we_Rising_LegsYtVideo,false),
            Exercise(R.string.weLH_Squats,R.string.weSquatsInstructions, R.drawable.bp2legs,true,true,R.string.bpBeine,false,null,false),
            Exercise(R.string.we_Lunges,R.string.we_LungesInstruction, R.drawable.ex_lunges,true,true,R.string.bpBeine,false,R.string.we_LungesYtVideo,false),
            Exercise(R.string.weSZ_Push_ups,R.string.wePush_upsInstructions, R.drawable.bp1arms,true,true,R.string.bpBrust,false,null,false),
            Exercise(R.string.weKH_Benchpress,R.string.weKH_BenchpressInstructions, R.drawable.ex_kh_benchpress,true,true,R.string.bpBrust,false,R.string.weKH_BenchpressYtVideo,false),
            Exercise(R.string.weLH_Benchpress,R.string.weLH_BenchpressInstructions, R.drawable.ex_lh_benchpress,true,true,R.string.bpBrust,false,R.string.weLH_BenchpressYtVideo,false),
            Exercise(R.string.weLH_Incline_Benchpress,R.string.weLH_InclineBenchpressInstructions, R.drawable.ex_lh_benchpress_inclined,true,true,R.string.bpBrust,false,R.string.weLH_InclineBenchpressYtVideo,false),
            Exercise(R.string.weKH_Incline_Benchpress,R.string.weKH_InclineBenchpressInstructions, R.drawable.ex_kh_benchpress_inclined,true,true,R.string.bpBrust,false,R.string.weKH_InclineBenchpressYtVideo,false),
            Exercise(R.string.we_Dips,R.string.we_DipsInstructions, R.drawable.ex_dips,true,true,R.string.bpBrust,false,null,false),


            Exercise(R.string.weLH_Row,R.string.weLH_RowInstructions, R.drawable.ex_lh_row,true,true,R.string.bpRücken,false,R.string.weLH_RowYtVideo,false),
            Exercise(R.string.weKH_Row,R.string.weKH_RowInstructions, R.drawable.ex_kh_row,true,true,R.string.bpRücken,false,R.string.weKH_RowYtVideo,false),


        )
    }

}