package data

import com.example.shapeminder_appidee.R
import model.Content

/*Meinem Repository habe ich eine neue Liste von Content hinzugefügt und
* als Membereigenschaft gespeichert.*/

class AppRepository {

    var content = loadContents()
    var exercises = loadExercises()
    var bodyParts = loadBodyparts()



    fun loadContents(): List<Content>{
        return listOf(
            Content(R.string.title1, R.string.text1, R.drawable.content1_img,false),
            Content(R.string.title2, R.string.title2,R.drawable.content2_img,false),
            Content(R.string.title3, R.string.title3, R.drawable.content3_img,false),
        )
    }





    fun loadExercises(): List<Content>{
        return listOf(
            Content(R.string.ecBenchpress, R.string.text1, R.drawable.content1_img,true),
            Content(R.string.ecDeadlift, R.string.title2,R.drawable.content2_img,true),
            Content(R.string.ecPullups, R.string.title3, R.drawable.content3_img,true),
            Content(R.string.ecSquats, R.string.text1, R.drawable.content1_img,true),
            Content(R.string.ecShoulderpress, R.string.title2,R.drawable.content1_img,true),

        )
    }


    fun loadBodyparts(): List<Content>{
        return listOf(
            Content(R.string.bpBauch, R.string.text1, R.drawable.bp5abs,true),
            Content(R.string.bpArme, R.string.title2,R.drawable.bp1arms,true),
            Content(R.string.bpSchulter, R.string.title3, R.drawable.bp3shoulders,true),
            Content(R.string.bpRücken, R.string.text1, R.drawable.bp4back,true),
            Content(R.string.bpBeine, R.string.title2,R.drawable.bp2legs,true),
            Content(R.string.bpBrust, R.string.title2,R.drawable.bp6chest,true),

            )
    }

    /*Problem: Meine Bilder von Übungen führen bei der Implentierung in
* in die loadExercise() Methode zum Absturz der App.*/

}