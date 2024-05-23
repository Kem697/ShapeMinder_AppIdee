package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.ListItemBinding
import de.kem697.shapeminder.model.data.local.model.myTraining.Content
import de.kem697.shapeminder.ui.viewModel.ContentViewModel


class ContentAdapter(
    private val dataset: List<Content>,
    private val contentViewModel: ContentViewModel,
): RecyclerView.Adapter<ContentAdapter.ContentItemViewHolder>(){

    inner class ContentItemViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContentItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        var content = dataset[position]
        if (holder is ContentItemViewHolder) {
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.materialCardView.setOnClickListener {
                contentViewModel.navigateContentDetailView(content)
                holder.binding.root.findNavController().navigate(R.id.action_homeScreen_to_homeContentDetailView)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}







