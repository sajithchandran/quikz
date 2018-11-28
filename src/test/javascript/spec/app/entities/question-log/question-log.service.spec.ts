/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { QuestionLogService } from 'app/entities/question-log/question-log.service';
import { IQuestionLog, QuestionLog, Answer, QuestionType, Difficulty, Status } from 'app/shared/model/question-log.model';

describe('Service Tests', () => {
    describe('QuestionLog Service', () => {
        let injector: TestBed;
        let service: QuestionLogService;
        let httpMock: HttpTestingController;
        let elemDefault: IQuestionLog;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(QuestionLogService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new QuestionLog(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Answer.A,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                QuestionType.TEXT,
                Difficulty.VERYEASY,
                Status.NEW
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        sendForApprovalDate: currentDate.format(DATE_TIME_FORMAT),
                        approvedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a QuestionLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        sendForApprovalDate: currentDate.format(DATE_TIME_FORMAT),
                        approvedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate,
                        sendForApprovalDate: currentDate,
                        approvedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new QuestionLog(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a QuestionLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        a: 'BBBBBB',
                        b: 'BBBBBB',
                        c: 'BBBBBB',
                        d: 'BBBBBB',
                        answer: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        sendForApprovalDate: currentDate.format(DATE_TIME_FORMAT),
                        approvedDate: currentDate.format(DATE_TIME_FORMAT),
                        questionType: 'BBBBBB',
                        difficulty: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate,
                        sendForApprovalDate: currentDate,
                        approvedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of QuestionLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        a: 'BBBBBB',
                        b: 'BBBBBB',
                        c: 'BBBBBB',
                        d: 'BBBBBB',
                        answer: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        sendForApprovalDate: currentDate.format(DATE_TIME_FORMAT),
                        approvedDate: currentDate.format(DATE_TIME_FORMAT),
                        questionType: 'BBBBBB',
                        difficulty: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate,
                        sendForApprovalDate: currentDate,
                        approvedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a QuestionLog', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
