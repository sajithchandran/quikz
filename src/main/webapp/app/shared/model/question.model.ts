import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICountry } from 'app/shared/model//country.model';
import { ICategory } from 'app/shared/model//category.model';
import { ITopic } from 'app/shared/model//topic.model';

export const enum Answer {
    A = 'A',
    B = 'B',
    C = 'C',
    D = 'D'
}

export const enum QuestionType {
    TEXT = 'TEXT',
    PICTURE = 'PICTURE',
    AUDIO = 'AUDIO',
    VIDEO = 'VIDEO'
}

export const enum Difficulty {
    VERYEASY = 'VERYEASY',
    EASY = 'EASY',
    MODERATE = 'MODERATE',
    SOMEWHATHARD = 'SOMEWHATHARD',
    HARD = 'HARD',
    VERYHARD = 'VERYHARD'
}

export const enum Status {
    NEW = 'NEW',
    WAITNG_FOR_APPROVAL = 'WAITNG_FOR_APPROVAL',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface IQuestion {
    id?: number;
    a?: string;
    b?: string;
    c?: string;
    d?: string;
    answer?: Answer;
    createdDate?: Moment;
    updatedDate?: Moment;
    sendForApprovalDate?: Moment;
    approvedDate?: Moment;
    questionType?: QuestionType;
    difficulty?: Difficulty;
    status?: Status;
    createdBy?: IUser;
    updatedBy?: IUser;
    approvedBy?: IUser;
    country?: ICountry;
    category?: ICategory;
    topic?: ITopic;
}

export class Question implements IQuestion {
    constructor(
        public id?: number,
        public a?: string,
        public b?: string,
        public c?: string,
        public d?: string,
        public answer?: Answer,
        public createdDate?: Moment,
        public updatedDate?: Moment,
        public sendForApprovalDate?: Moment,
        public approvedDate?: Moment,
        public questionType?: QuestionType,
        public difficulty?: Difficulty,
        public status?: Status,
        public createdBy?: IUser,
        public updatedBy?: IUser,
        public approvedBy?: IUser,
        public country?: ICountry,
        public category?: ICategory,
        public topic?: ITopic
    ) {}
}
