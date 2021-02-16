import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICustAccount, defaultValue } from 'app/shared/model/cust-account.model';

export const ACTION_TYPES = {
  FETCH_CUSTACCOUNT_LIST: 'custAccount/FETCH_CUSTACCOUNT_LIST',
  FETCH_CUSTACCOUNT: 'custAccount/FETCH_CUSTACCOUNT',
  CREATE_CUSTACCOUNT: 'custAccount/CREATE_CUSTACCOUNT',
  UPDATE_CUSTACCOUNT: 'custAccount/UPDATE_CUSTACCOUNT',
  DELETE_CUSTACCOUNT: 'custAccount/DELETE_CUSTACCOUNT',
  RESET: 'custAccount/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICustAccount>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CustAccountState = Readonly<typeof initialState>;

// Reducer

export default (state: CustAccountState = initialState, action): CustAccountState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CUSTACCOUNT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CUSTACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CUSTACCOUNT):
    case REQUEST(ACTION_TYPES.UPDATE_CUSTACCOUNT):
    case REQUEST(ACTION_TYPES.DELETE_CUSTACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CUSTACCOUNT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CUSTACCOUNT):
    case FAILURE(ACTION_TYPES.CREATE_CUSTACCOUNT):
    case FAILURE(ACTION_TYPES.UPDATE_CUSTACCOUNT):
    case FAILURE(ACTION_TYPES.DELETE_CUSTACCOUNT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTACCOUNT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTACCOUNT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CUSTACCOUNT):
    case SUCCESS(ACTION_TYPES.UPDATE_CUSTACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CUSTACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cust-accounts';

// Actions

export const getEntities: ICrudGetAllAction<ICustAccount> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CUSTACCOUNT_LIST,
  payload: axios.get<ICustAccount>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICustAccount> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CUSTACCOUNT,
    payload: axios.get<ICustAccount>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICustAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CUSTACCOUNT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICustAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CUSTACCOUNT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICustAccount> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CUSTACCOUNT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
