/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QuikzTestModule } from '../../../test.module';
import { QuestionLogUpdateComponent } from 'app/entities/question-log/question-log-update.component';
import { QuestionLogService } from 'app/entities/question-log/question-log.service';
import { QuestionLog } from 'app/shared/model/question-log.model';

describe('Component Tests', () => {
    describe('QuestionLog Management Update Component', () => {
        let comp: QuestionLogUpdateComponent;
        let fixture: ComponentFixture<QuestionLogUpdateComponent>;
        let service: QuestionLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QuikzTestModule],
                declarations: [QuestionLogUpdateComponent]
            })
                .overrideTemplate(QuestionLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionLogService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionLog(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new QuestionLog();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.questionLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
