package data

import com.example.shapeminder_appidee.R
import model.Content

/*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

class AppRepository {

    var content = loadContents()
    var exercises = loadExercises()
    var bodyParts = loadBodyparts()
    var exercisesByBodyparts = loadExercisesByBodypart()



    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img,false,false),
            Content(R.string.title2, R.string.title2,R.drawable.content2_img,false,false),
            Content(R.string.title3, R.string.title3, R.drawable.content3_img,false,false),
        )
    }





    fun loadExercises(): List<Content>{
        return listOf(
            Content(R.string.ecBenchpress, R.string.text1, R.drawable.content1_img,true,false),
            Content(R.string.ecDeadlift, R.string.title2,R.drawable.content2_img,true,false),
            Content(R.string.ecPullups, R.string.title3, R.drawable.content3_img,true,false),
            Content(R.string.ecSquats, R.string.text1, R.drawable.content1_img,true,false),
            Content(R.string.ecShoulderpress, R.string.title2,R.drawable.content1_img,true,false),

        )
    }


    fun loadBodyparts(): List<Content>{
        return listOf(
            Content(R.string.bpBauch, R.string.text1, R.drawable.bp5abs,true,false),
            Content(R.string.bpArme, R.string.title2,R.drawable.bp1arms,true,false),
            Content(R.string.bpSchulter, R.string.title3, R.drawable.bp3shoulders,true,false),
            Content(R.string.bpRücken, R.string.text1, R.drawable.bp4back,true,false),
            Content(R.string.bpBeine, R.string.title2,R.drawable.bp2legs,true,false),
            Content(R.string.bpBrust, R.string.title2,R.drawable.bp6chest,true,false),

            )
    }


    fun loadExercisesByBodypart(): List<Content>{
        return listOf(
            Content(R.string.weBicepscurls,R.string.weText1, R.drawable.bp1arms,true,true),
        )
    }



}