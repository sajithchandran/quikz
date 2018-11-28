import { ICategory } from 'app/shared/model//category.model';

export interface ITopic {
    id?: number;
    name?: string;
    category?: ICategory;
}

export class Topic implements ITopic {
    constructor(public id?: number, public name?: string, public category?: ICategory) {}
}
