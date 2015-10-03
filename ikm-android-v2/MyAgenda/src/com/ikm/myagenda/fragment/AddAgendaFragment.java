package com.ikm.myagenda.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.AgendaDetailVO;
import com.ikm.myagenda.data.AgendaHeader;
import com.ikm.myagenda.data.AgendaHeaderVO;
import com.ikm.myagenda.data.AgendaItems;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.RespListAgendaVO;
import com.ikm.myagenda.fragment.MyAgendaFragment.ReqListAgendaTask;
import com.ikm.myagenda.util.SharedPreferencesUtils;

public class AddAgendaFragment extends Fragment {
	private static final String TAG = AddAgendaFragment.class.getSimpleName();
	private ImageView mImage;
	private TextView mName;
	private TextView mPlace;
	private Context ctx;
	View rootView;
	private ReqListAgendaTask reqListAgendaTask = null;

	public static AddAgendaFragment newInstance() {
		return new AddAgendaFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_add_myagenda,
				container, false);
		ctx = this.getActivity().getBaseContext();
		mImage = (ImageView) rootView.findViewById(R.id.expandable_lv_social_image);
		mName = (TextView) rootView.findViewById(R.id.expandable_lv_social_name);
		mPlace = (TextView) rootView.findViewById(R.id.expandable_lv_social_place);
//		ImageUtil.displayRoundImage(mImage,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		mName.setText("Agenda "+ loginData.getNama());
		mPlace.setText(ctx.getResources().getString(R.string.school_name));
		
		
		return rootView;
	}
	
	private List<AgendaHeader> fillDataInterface(List<AgendaHeader> items,RespListAgendaVO respListAgendaVO) {
		
		for (AgendaHeaderVO headerVO : respListAgendaVO.getListAgendaHeaderVO()) {
			AgendaHeader item = new AgendaHeader();
			item.setTitle(headerVO.getTanggalAgendaVal());
				for (AgendaDetailVO agendaDetailVO : headerVO.getAgendaDetail()) {
					AgendaItems child = new AgendaItems();					
					child.setTitle(agendaDetailVO.getSubject()+" : "+agendaDetailVO.getIsiAgenda());
					item.getItemsDetail().add(child);					
				}
			items.add(item);
		}		
		return items;
	}
	
}
