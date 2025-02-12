package com.example.ehcf_doctor.MyPatient.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ehcf.Upload.model.ModelGetAllReport
import com.example.ehcf_doctor.MyPatient.model.Prescription
import com.example.ehcf_doctor.Prescription.activity.ViewReport
import com.example.ehcf_doctor.R


//class AdapterViewReport(private val list: ModelGetAllReport, val context: Context,) :
//    RecyclerView.Adapter<AdapterViewReport.MyViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.single_row_view_all_report, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        if(list.result[position].title!=null){
//            holder.reportCount.text= list.result[position].title.toString()
//
//        }
//
//
//        holder.btnViewReport.setOnClickListener {
//            val intent = Intent(context as Activity, ViewReport::class.java)
//                .putExtra("report",list.result[position].report.toString())
//            context.startActivity(intent)
//        }
//
//        // Glide.with(hol der.image).load(list[position].url).into(holder.image)
//
//
//    }
//
//
//    override fun getItemCount(): Int {
//        return list.result.size
//
//    }
//
//    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val btnViewReport: TextView = itemView.findViewById(R.id.btnViewReportViewAll)
//            val reportCount: TextView = itemView.findViewById(R.id.tvReportCount)
//            val cardView: CardView = itemView.findViewById(R.id.cardViewViewRe)
//
//    }
//}




class AdapterViewReport(private val prescriptionList: List<Prescription>, val context: Context) :
    RecyclerView.Adapter<AdapterViewReport.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_row_view_all_report, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val prescription = prescriptionList[position]
        holder.reportCount.text = prescription.customer_name
        holder.tvTestName.text = prescription.test_name
        holder.tvInstraction.text = prescription.instructions
        holder.btnViewReport.setOnClickListener {
            val intent = Intent(context as Activity, ViewReport::class.java)
                .putExtra("report", prescription.test_report)
            context.startActivity(intent)
        }
        // Glide.with(holder.image).load(prescription.test_report).into(holder.image)
    }

    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnViewReport: TextView = itemView.findViewById(R.id.btnViewReportViewAll)
        val reportCount: TextView = itemView.findViewById(R.id.tvCustomerName)
        val tvTestName: TextView = itemView.findViewById(R.id.tvTestName)
        val tvInstraction: TextView = itemView.findViewById(R.id.tvInstraction)
        val cardView: CardView = itemView.findViewById(R.id.cardViewViewRe)
        // val image: ImageView = itemView.findViewById(R.id.imageViewReport)
    }
}
