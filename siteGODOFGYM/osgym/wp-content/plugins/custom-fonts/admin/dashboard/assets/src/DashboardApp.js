import React from 'react';
import ReactDOM from 'react-dom';

/* Main Component */
import './common/common';
import './common/all-config.scss';
import SettingsWrap from '@DashboardApp/SettingsWrap';
import { Provider } from 'react-redux';
import globalDataStore from '@Admin/store/globalDataStore';
import setInitialState  from '@Utils/setInitialState';

const currentState = globalDataStore.getState();

if ( ! currentState.initialStateSetFlag ) {
	setInitialState( globalDataStore );
}

ReactDOM.render(
	<Provider store={globalDataStore}>
		<SettingsWrap/>
	</Provider>,
	document.getElementById( 'bsf-custom-font-dashboard-app' )
);
