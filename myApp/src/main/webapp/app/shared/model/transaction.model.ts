import { Moment } from 'moment';
import { ICustAccount } from 'app/shared/model/cust-account.model';

export interface ITransaction {
  id?: number;
  date?: string;
  amount?: number;
  custAccount?: ICustAccount;
}

export const defaultValue: Readonly<ITransaction> = {};
