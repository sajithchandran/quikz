import { ITopic } from 'app/shared/model//topic.model';

export interface ICategory {
    id?: number;
    name?: string;
    topics?: ITopic[];
}

export class Category implements ICategory {
    constructor(public id?: number, public name?: string, public topics?: ITopic[]) {}
}
