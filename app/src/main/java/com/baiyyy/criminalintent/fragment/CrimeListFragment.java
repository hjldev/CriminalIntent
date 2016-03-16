package com.baiyyy.criminalintent.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyyy.criminalintent.R;
import com.baiyyy.criminalintent.activity.CrimePagerActivity;
import com.baiyyy.criminalintent.bean.Crime;
import com.baiyyy.criminalintent.bean.CrimeLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjinlong on 2016/1/4.
 */
public class CrimeListFragment extends ListFragment{

    private ArrayList<Crime> mCrime;
    private static final String TAG = "CrimeListFragment";

    // 使用接口，实现Fragment与Activity的解耦
    private Callbacks callbacks;
    public interface Callbacks{
        void onItemSelect(Crime crime);
    }

    // 重写onAttach方法跟onDetach方法关联Activity和取消
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    public void updateUI(){
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title);
        mCrime = CrimeLab.get(getActivity()).getmCrime();
//        ArrayAdapter<Crime> arrayAdapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrime);
        setListAdapter(new CrimeAdapter(mCrime));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            registerForContextMenu(listView);
        } else{
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater menuInflater = mode.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_crime_list_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_delete:
                            CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--){
                                if (getListView().isItemChecked(i)){
                                    crimeLab.delete(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        return view;
    }

    private class CrimeAdapter extends ArrayAdapter<Crime>{
        ViewHolder holder = null;

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                holder = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_crime_list, null);
                holder.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
                holder.date_tv = (TextView) convertView.findViewById(R.id.date_tv);
                holder.status_cb = (CheckBox) convertView.findViewById(R.id.status_cb);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title_tv.setText(getItem(position).getmTitle());
            holder.date_tv.setText(getItem(position).getmDate().toString());
            holder.status_cb.setChecked(getItem(position).isSolved());
            return convertView;
        }
    }

    static class ViewHolder{
        TextView title_tv;
        TextView date_tv;
        CheckBox status_cb;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = (Crime) getListAdapter().getItem(position);
//        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
//        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getmId());
//        startActivity(intent);
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        Fragment fragment = CrimeFragment.newInstance(c.getmId());
//        fm.beginTransaction().replace(R.id.detailFragmentContainer, fragment).commit();
        callbacks.onItemSelect(c);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                CrimeLab.get(getActivity()).add(new Crime());
                ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_crime_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CrimeAdapter crimeAdapter = (CrimeAdapter) getListAdapter();
        switch (item.getItemId()){
            case R.id.menu_delete:
                CrimeLab.get(getActivity()).delete(crimeAdapter.getItem(info.position));
                crimeAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
