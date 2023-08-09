package com.example.note10120050.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.note10120050.entity.DailyNote;
import com.example.note10120050.databinding.NoteItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// NIM : 10120050
// Nama : Ari Syafri
// Kelas : IF2
public class NoteAdapter extends FirebaseRecyclerAdapter<DailyNote, NoteAdapter.NoteViewHolder> {

    private OnItemClickListener itemClickListener;

    // Define the interface
    public interface OnItemClickListener {
        void onItemClick(DailyNote item, String noteKey);
    }

    public NoteAdapter(FirebaseRecyclerOptions<DailyNote> options, OnItemClickListener listener) {
        super(options);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindViewHolder(NoteViewHolder holder, int position, DailyNote model) {
        holder.bind(model);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteItemBinding binding = NoteItemBinding.inflate(inflater, parent, false);
        return new NoteViewHolder(binding);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final NoteItemBinding binding;

        public NoteViewHolder(NoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Set click listener for the item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                    itemClickListener.onItemClick(getItem(position), getRef(position).getKey()); // Pass the noteKey to the click listener
                }
            });
        }

        public void bind(DailyNote item) {
            binding.tvTitleItem.setText(item.getTitle());
            binding.tvCategoryItem.setText(item.getCategory());
            binding.tvDateItem.setText(item.getDate());
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}