package com.example.musicplayer;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.musicplayer.databinding.FragmentPlayListBindingImpl;
import com.example.musicplayer.databinding.FragmentPlayedBindingImpl;
import com.example.musicplayer.databinding.ItemMusicListBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_FRAGMENTPLAYLIST = 1;

  private static final int LAYOUT_FRAGMENTPLAYED = 2;

  private static final int LAYOUT_ITEMMUSICLIST = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.musicplayer.R.layout.fragment_play_list, LAYOUT_FRAGMENTPLAYLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.musicplayer.R.layout.fragment_played, LAYOUT_FRAGMENTPLAYED);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.musicplayer.R.layout.item_music_list, LAYOUT_ITEMMUSICLIST);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_FRAGMENTPLAYLIST: {
          if ("layout/fragment_play_list_0".equals(tag)) {
            return new FragmentPlayListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_play_list is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPLAYED: {
          if ("layout/fragment_played_0".equals(tag)) {
            return new FragmentPlayedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_played is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMMUSICLIST: {
          if ("layout/item_music_list_0".equals(tag)) {
            return new ItemMusicListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_music_list is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "music");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/fragment_play_list_0", com.example.musicplayer.R.layout.fragment_play_list);
      sKeys.put("layout/fragment_played_0", com.example.musicplayer.R.layout.fragment_played);
      sKeys.put("layout/item_music_list_0", com.example.musicplayer.R.layout.item_music_list);
    }
  }
}
