/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QuikzTestModule } from '../../../test.module';
import { QuestionLogDeleteDialogComponent } from 'app/entities/question-log/question-log-delete-dialog.component';
import { QuestionLogService } from 'app/entities/question-log/question-log.service';

describe('Component Tests', () => {
    describe('QuestionLog Management Delete Component', () => {
        let comp: QuestionLogDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionLogDeleteDialogComponent>;
        let service: QuestionLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QuikzTestModule],
                declarations: [QuestionLogDeleteDialogComponent]
            })
                .overrideTemplate(QuestionLogDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
