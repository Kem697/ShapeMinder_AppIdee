package ui.bottomNav.myNutritionScreen.nav2foodScanner


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.BarcodeScan
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodscannerNav2Binding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import ui.viewModel.NutrionViewModel


class FoodScannerNav2Fragment : Fragment() {
    private lateinit var binding: FragmentFoodscannerNav2Binding
    val nutritionViewModel: NutrionViewModel by activityViewModels()
    private lateinit var barlauncher : ActivityResultLauncher<ScanOptions>


    /*Barcode konnte gescannt werden. Jedoch ist Bildschirm schwarz. Überarbeitung notwendig! Sobald
    * einmal die Kamera einen Code rescannt hat, bleibt sie schwarz*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodscannerNav2Binding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invokeBarcodeScanner()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        releaseCameraSource()
    }



    fun invokeBarcodeScanner() {
        var barcodScanBtn = binding.barcodeScanBtn
        barcodScanBtn.setOnClickListener {
            scanCode()
        }
    }




    /*EN:
    * Write Comment*/
    fun setUp(){
        barlauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                nutritionViewModel.searchFoodByBarcode(result.contents)
                nutritionViewModel.scannedFood.observe(viewLifecycleOwner){food->
                    Log.i("Barcode","Searchfood:: ${food.url}")
                    AlertDialog.Builder(context).apply {
                        setTitle(getString(R.string.barcodeResult))
                        setMessage(food.productNameDe)

                        setNegativeButton("Abbrechen"){dialog,_->
                            dialog.dismiss()
                        }

                        setPositiveButton("Speichern") { dialog, _ ->
                            nutritionViewModel.isSaved(!food.isSaved,food)
                            food.isSaved = true
//                            nutritionViewModel.insertProduct(food)
                            dialog.dismiss()
                        }

                        show()
                    }
                }

            }
        }
    }





    private fun scanCode() {
        val options = ScanOptions().apply {
            setPrompt(getString(R.string.cameraPromptHint))
            setBeepEnabled(true)
            setOrientationLocked(true)
            setCaptureActivity(BarcodeScan::class.java)
        }
        barlauncher.launch(options)
    }


    fun releaseCameraSource(){
        if (::barlauncher.isInitialized){
            barlauncher.unregister()
            Log.i("FoodScannerNav2Fragment", "Camera source released")
        }
    }
}