import { Moment } from 'moment';
import { IQuestion } from 'app/shared/model//question.model';

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

export interface IQuestionLog {
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
    question?: IQuestion;
}

export class QuestionLog implements IQuestionLog {
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
        public question?: IQuestion
    ) {}
}
