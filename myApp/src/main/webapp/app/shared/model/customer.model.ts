import { ICustAccount } from 'app/shared/model/cust-account.model';

export interface ICustomer {
  id?: number;
  fname?: string;
  lname?: string;
  custAccounts?: ICustAccount[];
}

export const defaultValue: Readonly<ICustomer> = {};
