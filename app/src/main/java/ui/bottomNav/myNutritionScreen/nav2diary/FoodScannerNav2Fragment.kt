package ui.bottomNav.myNutritionScreen.nav2diary


import android.app.AlertDialog
import android.os.Bundle
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


    /*Barcode konnte gescannt werden. Jedoch ist Bildschirm schwarz. Überarbeitung notwendig!*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodscannerNav2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        invokeBarcodeScanner()
    }

    fun invokeBarcodeScanner() {
        var barcodScanBtn = binding.barcodeScanBtn
        barcodScanBtn.setOnClickListener {
            scanCode()
        }
    }




    fun setUp(){
        barlauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle(context?.getString(R.string.barcodeResult))
                builder.setMessage(result.contents)
                builder.setPositiveButton("OK") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.show()
            }
        }
    }



    private fun scanCode() {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
        options.setOrientationLocked(true)
        options.setCaptureActivity(BarcodeScan::class.java)
        barlauncher.launch(options)
    }

}