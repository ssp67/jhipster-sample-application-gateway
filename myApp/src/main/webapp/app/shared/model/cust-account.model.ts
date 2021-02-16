import { ITransaction } from 'app/shared/model/transaction.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface ICustAccount {
  id?: number;
  departmentName?: string;
  accountNumber?: string;
  accountType?: string;
  transactions?: ITransaction[];
  customer?: ICustomer;
}

export const defaultValue: Readonly<ICustAccount> = {};
