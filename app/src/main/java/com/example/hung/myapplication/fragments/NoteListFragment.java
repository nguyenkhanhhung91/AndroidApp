package com.example.hung.myapplication.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.activities.ViewModel;
import com.example.hung.myapplication.data.object_define.Note;
import com.example.hung.myapplication.interfaces.ListInterface;

import java.util.List;

/**
 * Created by Hung on 10/20/2017.
 */

public class NoteListFragment extends android.support.v4.app.Fragment{
    private RecyclerView mPlayerRecycleView;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ViewModel mViewModel;
    String currentActivity,currentEmail;
    List<Note> notes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.list, container, false);
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mPlayerRecycleView = (RecyclerView) v.findViewById(R.id.rvList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPlayerRecycleView.setLayoutManager(mLayoutManager);
        currentActivity = getArguments().getString("ACTIVITY");
        currentEmail= getArguments().getString("USER_EMAIL");
        updateUI();
        return v;
    }
    public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>  {
        public List<Note> mNotes;
        private Context context;

        public NoteAdapter(Context context, List<Note> Notes){
            this.mNotes= Notes;
            this.context = context;
        }
        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new NoteHolder(itemView);
        }
        @Override
        public void onBindViewHolder(NoteHolder holder, int position){
            holder.title.setText(mNotes.get(position).getTitle());
            holder.tagString.setText(mNotes.get(position).getTagstring());
        }

        @Override
        public int getItemCount(){
            if(mNotes==null)
                return 0;
            return mNotes.size();
        }


        public class NoteHolder extends RecyclerView.ViewHolder {
            public TextView title, tagString;

            public NoteHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.Note_title);
                tagString = (TextView) view.findViewById(R.id.Note_TagString);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        int pos = getAdapterPosition();
                        ((ListInterface)getActivity()).GetInfoFromList(mNotes.get(pos).getNoteid());
                    }
                });
            }
        }
    }
    private void updateUI(){
        if(currentActivity.equals("admin"))
            notes= mViewModel.getNotesforAdmin();
        if(currentActivity.equals("user"))
            notes = mViewModel.getNotesforUser(currentEmail);
        mAdapter =  new NoteAdapter(getActivity(),notes);
        mPlayerRecycleView.setAdapter(mAdapter);
    }
}
