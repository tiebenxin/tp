package com.example.haoyuban111.mubanapplication.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.adapter.AdapterHostListSingle;
import com.example.haoyuban111.mubanapplication.controller.ControllerBooks;
import com.example.haoyuban111.mubanapplication.controller.ControllerBottomTab;
import com.example.haoyuban111.mubanapplication.controller.ControllerHeader;
import com.example.haoyuban111.mubanapplication.help_class.IEventListener;
import com.example.haoyuban111.mubanapplication.help_class.IFragmentBaseExtended;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerEighth;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerFifth;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerFirst;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerForth;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerSecond;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerSeventh;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerSixth;
import com.example.haoyuban111.mubanapplication.ui.fragment.FragmentRecyclerThird;

public class ActivitySecond extends BaseActivity implements View.OnClickListener, IEventListener {

    private ENavigationManageTabs CURRENT_TAB = ENavigationManageTabs.FIRST;
    private ControllerHeader viewHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        showFragment(CURRENT_TAB);
    }


    public void showFragment(ENavigationManageTabs tab) {
        if (isFinishing()) return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newFragment = fragmentManager.findFragmentByTag(tab.toString());
        Fragment oldFragment = CURRENT_TAB == null ? null : fragmentManager.findFragmentByTag(CURRENT_TAB.toString());

        if (newFragment == null) {
            newFragment = createFragment(tab);
        }


        if (oldFragment != null && oldFragment instanceof IFragmentBaseExtended) {
            ((IFragmentBaseExtended) oldFragment).setListener(null);
        }

        if (newFragment instanceof IFragmentBaseExtended) {
            IFragmentBaseExtended fragment = (IFragmentBaseExtended) newFragment;
            fragment.setListener(this);
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//渐变动画
        ft.replace(R.id.fl_content, newFragment, tab.toString());
        ft.attach(newFragment);
        ft.commitAllowingStateLoss();

        CURRENT_TAB = tab;
        initTitle();
    }


    private Fragment createFragment(ENavigationManageTabs tab) {
        switch (tab) {
            case FIRST:
                return new FragmentRecyclerFirst();
            case SECOND:
                return new FragmentRecyclerSecond();
            case THIRD:
                return new FragmentRecyclerThird();
            case FORTH:
                return new FragmentRecyclerForth();
            case FIFTH:
                return new FragmentRecyclerFifth();
            case SIXTH:
                return new FragmentRecyclerSixth();
            case SEVENTH:
                return new FragmentRecyclerSeventh();
            case EIGHTH:
                return new FragmentRecyclerEighth();

        }

        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        ControllerBottomTab mBottomTabs = new ControllerBottomTab(this, findViewById(R.id.viewBottom), ControllerBottomTab.EBottomTabs.SECOND);

        viewHeader = new ControllerHeader(findViewById(R.id.viewHeader));
        viewHeader.setOnClickListener(new ControllerHeader.OnClickListener() {
            @Override
            public void clickLeft() {
                if (CURRENT_TAB == ENavigationManageTabs.FIRST) {
                    finish();
                } else if (CURRENT_TAB == ENavigationManageTabs.SECOND) {
                    showFragment(ENavigationManageTabs.FIRST);
                } else if (CURRENT_TAB == ENavigationManageTabs.THIRD) {
                    showFragment(ENavigationManageTabs.SECOND);
                } else if (CURRENT_TAB == ENavigationManageTabs.FORTH) {
                    showFragment(ENavigationManageTabs.THIRD);
                } else if (CURRENT_TAB == ENavigationManageTabs.FIFTH) {
                    showFragment(ENavigationManageTabs.FORTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.SIXTH) {
                    showFragment(ENavigationManageTabs.FIFTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.SEVENTH) {
                    showFragment(ENavigationManageTabs.SIXTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.EIGHTH) {
                    showFragment(ENavigationManageTabs.SEVENTH);
                }

            }

            @Override
            public void clickRight() {
                if (CURRENT_TAB == ENavigationManageTabs.FIRST) {
                    showFragment(ENavigationManageTabs.SECOND);
                } else if (CURRENT_TAB == ENavigationManageTabs.SECOND) {
                    showFragment(ENavigationManageTabs.THIRD);
                } else if (CURRENT_TAB == ENavigationManageTabs.THIRD) {
                    showFragment(ENavigationManageTabs.FORTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.FORTH) {
                    showFragment(ENavigationManageTabs.FIFTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.FIFTH) {
                    showFragment(ENavigationManageTabs.SIXTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.SIXTH) {
                    showFragment(ENavigationManageTabs.SEVENTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.SEVENTH) {
                    showFragment(ENavigationManageTabs.EIGHTH);
                } else if (CURRENT_TAB == ENavigationManageTabs.EIGHTH) {
                    showFragment(ENavigationManageTabs.FIRST);
                }
            }
        });


    }

    private void initTitle() {
        if (CURRENT_TAB == ENavigationManageTabs.FIRST) {
            viewHeader.setTitle("First");
        } else if (CURRENT_TAB == ENavigationManageTabs.SECOND) {
            viewHeader.setTitle("Second");
        } else if (CURRENT_TAB == ENavigationManageTabs.THIRD) {
            viewHeader.setTitle("Third");
        } else if (CURRENT_TAB == ENavigationManageTabs.FORTH) {
            viewHeader.setTitle("Forth");
        } else if (CURRENT_TAB == ENavigationManageTabs.FIFTH) {
            viewHeader.setTitle("Fifth");
        } else if (CURRENT_TAB == ENavigationManageTabs.SIXTH) {
            viewHeader.setTitle("Sixth");
        } else if (CURRENT_TAB == ENavigationManageTabs.SEVENTH) {
            viewHeader.setTitle("Seventh");
        } else if (CURRENT_TAB == ENavigationManageTabs.EIGHTH) {
            viewHeader.setTitle("Eighth");
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onListeningEvent(Object sender, int eventId, Object data) {

    }


    public enum ENavigationManageTabs {
        FIRST(0),
        SECOND(1),
        THIRD(2),
        FORTH(3),
        FIFTH(4),
        SIXTH(5),
        SEVENTH(6),
        EIGHTH(7),
        NINTH(8),
        THNTH(9);

        private final int _value;

        public int getValue() {
            return _value;
        }

        ENavigationManageTabs(int value) {
            _value = value;
        }

        public static ENavigationManageTabs fromInt(Integer value) {
            ENavigationManageTabs result = null;
            for (ENavigationManageTabs item : ENavigationManageTabs.values()) {
                if (item.getValue() == value) {
                    result = item;
                    break;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("ENavigationManageTabs - fromInt");
            }
            return result;
        }
    }
}
