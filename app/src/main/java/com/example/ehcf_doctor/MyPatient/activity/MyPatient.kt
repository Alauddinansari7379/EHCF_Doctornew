package com.example.ehcf_doctor.MyPatient.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ehcf.Helper.myToast
import com.example.ehcf.sharedpreferences.SessionManager
import com.example.ehcf_doctor.ManageSlots.adapter.AdapterSlotsList
import com.example.ehcf_doctor.MyPatient.adapter.AdapterMyPatient
import com.example.ehcf_doctor.MyPatient.model.ModelMyPatient
import com.example.ehcf_doctor.Prescription.adapter.AdapterPrescription
import com.example.ehcf_doctor.R
import com.example.ehcf_doctor.databinding.ActivityMyPatientBinding
import com.example.myrecyview.apiclient.ApiClient
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPatient : AppCompatActivity() {
    private var context: Context = this@MyPatient
    var progressDialog: ProgressDialog? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityMyPatientBinding
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        shimmerFrameLayout = findViewById(R.id.shimmer_myPatient)
        shimmerFrameLayout!!.startShimmer();

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.imgRefresh.setOnClickListener {
            overridePendingTransition(0, 0)
            finish()
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        binding.imgSearch.setOnClickListener {
            if (binding.edtSearch.text.isEmpty()){
                binding.edtSearch.error="Enter Patient Name"
                binding.edtSearch.requestFocus()
            }else{
                apiCallSearchMyPatient()

            }
        }

        apiCallMyPatient()
    }

    private fun apiCallMyPatient() {
        progressDialog = ProgressDialog(this@MyPatient)
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)
        // progressDialog!!.show()

        ApiClient.apiService.getPatients(sessionManager.id.toString())
            .enqueue(object : Callback<ModelMyPatient> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelMyPatient>, response: Response<ModelMyPatient>
                ) {
                    if (response.body()!!.result.isEmpty()) {
                        binding.tvNoDataFound.visibility = View.VISIBLE
                        binding.shimmerMyPatient.visibility = View.GONE
                        // myToast(requireActivity(),"No Data Found")
                        progressDialog!!.dismiss()

                    } else {
                        binding.recyclerView.apply {
                            shimmerFrameLayout?.startShimmer()
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.shimmerMyPatient.visibility = View.GONE
                            binding.tvNoDataFound.visibility = View.GONE
                            adapter = AdapterMyPatient(this@MyPatient, response.body()!!)
                            progressDialog!!.dismiss()

                        }
                    }
                }

                override fun onFailure(call: Call<ModelMyPatient>, t: Throwable) {
                    myToast(this@MyPatient, "Something went wrong")
                    binding.shimmerMyPatient.visibility = View.GONE
                    progressDialog!!.dismiss()

                }

            })
    }

    private fun apiCallSearchMyPatient() {
        progressDialog = ProgressDialog(this@MyPatient)
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)
         progressDialog!!.show()
        val patientName = binding.edtSearch.text.toString()
        ApiClient.apiService.searchPatient(sessionManager.id.toString(),patientName)
            .enqueue(object : Callback<ModelMyPatient> {
                @SuppressLint("LogNotTimber")
                override fun onResponse(
                    call: Call<ModelMyPatient>, response: Response<ModelMyPatient>
                ) {
                    if (response.code() == 500) {
                        myToast(this@MyPatient, "Server Error")
                        binding.shimmerMyPatient.visibility = View.GONE
                        progressDialog!!.dismiss()

                    }
                    else if (response.body()!!.status == 0) {
                        binding.tvNoDataFound.visibility = View.VISIBLE
                        binding.shimmerMyPatient.visibility = View.GONE
                        myToast(this@MyPatient, "${response.body()!!.message}")
                        progressDialog!!.dismiss()

                    } else if (response.body()!!.result.isEmpty()) {
                        binding.recyclerView.adapter =
                            AdapterMyPatient(this@MyPatient, response.body()!!)
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                        binding.tvNoDataFound.visibility = View.VISIBLE
                        binding.shimmerMyPatient.visibility = View.GONE
                        myToast(this@MyPatient, "No Patient Found")
                        progressDialog!!.dismiss()

                    } else {
                        binding.recyclerView.adapter =
                            AdapterMyPatient(this@MyPatient, response.body()!!, )
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                        binding.tvNoDataFound.visibility = View.GONE
                        shimmerFrameLayout?.startShimmer()
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.shimmerMyPatient.visibility = View.GONE
                        progressDialog!!.dismiss()
//                        binding.rvManageSlot.apply {
//                            binding.tvNoDataFound.visibility = View.GONE
//                            shimmerFrameLayout?.startShimmer()
//                            binding.rvManageSlot.visibility = View.VISIBLE
//                            binding.shimmerMySlot.visibility = View.GONE
//                            // myToast(this@ShuduleTiming, response.body()!!.message)
//                            adapter = AdapterSlotsList(this@MySlot, response.body()!!, this@MySlot)
//                            progressDialog!!.dismiss()
//
//                        }
                    }
                }

                override fun onFailure(call: Call<ModelMyPatient>, t: Throwable) {
                    myToast(this@MyPatient, "Something went wrong")
                    binding.shimmerMyPatient.visibility = View.GONE
                    progressDialog!!.dismiss()

                }

            })
    }


}